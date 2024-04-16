package org.pgs.postp.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.pgs.postp.dto.auth.request.AuthRequestDTO;
import org.pgs.postp.dto.auth.response.JwtResponseDTO;
import org.pgs.postp.model.UserModel;
import org.pgs.postp.model.RoleModel;
import org.pgs.postp.service.Impl.JwtService;
import org.pgs.postp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth/api/v1")
@CrossOrigin( origins = "http://Localhost:4200")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public JwtResponseDTO authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
            String token = jwtService.GenerateToken(authRequestDTO.getUsername());
            UserModel user = userService.findByUsername(authRequestDTO.getUsername());

            Set<String> roleNames = user.getRoles().stream()
                    .map(RoleModel::getRoleName)
                    .collect(Collectors.toSet());

            return JwtResponseDTO.builder()
                    .accessToken(token)
                    .userID(user.getUserID())
                    .username(user.getUsername())
                    .name(user.getName())
                    .email(user.getEmail())
                    .roles(roleNames)
                    .build();
        } catch (AuthenticationException e) {
            throw new UsernameNotFoundException("Invalid user request..!!");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('MANAGER') || hasAuthority('CASHIER')")
    @GetMapping("/ping")
    public String test() {
        try {
            return "Welcome";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            String token = extractTokenFromRequest(request);
            // Add the token to the list of invalidated tokens
            jwtService.invalidateToken(token);
            // Clear the security context, effectively logging out the user
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok().body("Logout successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Logout failed: " + e.getMessage());
        }
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }

        throw new RuntimeException("Authorization header is missing or invalid");
    }
}

