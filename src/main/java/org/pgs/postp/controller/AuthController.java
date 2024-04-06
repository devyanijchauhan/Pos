package org.pgs.postp.controller;

import org.pgs.postp.dto.auth.request.AuthRequestDTO;
import org.pgs.postp.dto.auth.response.JwtResponseDTO;
import org.pgs.postp.service.Impl.JwtService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/api/v1")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public JwtResponseDTO authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
            String token = jwtService.GenerateToken(authRequestDTO.getUsername());
            return JwtResponseDTO.builder().accessToken(token).build();
        } catch (AuthenticationException e) {
            throw new UsernameNotFoundException("Invalid user request..!!");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('MANAGER') || hasAuthority('CASHIER')")
    @GetMapping("/ping")
    public String test() {
        try {
            return "Welcome";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
