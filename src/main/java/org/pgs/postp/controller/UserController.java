package org.pgs.postp.controller;

import org.pgs.postp.dto.UserDTO;
import org.pgs.postp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.pgs.postp.dto.ResetPasswordRequest;

import java.util.List;
import org.springframework.http.MediaType;
import java.util.HashMap;
import java.util.Map;

@RestController
@PreAuthorize("hasAuthority('ADMIN') || hasAuthority('MANAGER')")
@RequestMapping("/api/users")
@CrossOrigin( origins = "http://Localhost:4200")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        UserDTO user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {
        userDTO.setUserID(id);
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/reset-password/{userId}")
    public ResponseEntity<?> resetPassword(@PathVariable Long userId, @RequestBody ResetPasswordRequest resetPasswordRequest) {
        try {
            userService.resetUserPassword(userId, resetPasswordRequest.getNewPassword());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Password reset successfully.");
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
