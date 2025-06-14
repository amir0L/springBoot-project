package com.amir.RESTfulApi.user;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Create a new user
    @PostMapping
    public ResponseEntity<UserDto.UserResponse> createUser(
            @RequestBody UserDto.CreateUserRequest request) {
        try {
            UserDto.UserResponse user = userService.createUser(request);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<UserDto.UserResponse>> getAllUsers() {
        try {
            List<UserDto.UserResponse> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto.UserResponse> getUserById(@PathVariable Integer userId) {
        try {
            Optional<UserDto.UserResponse> user = userService.getUserById(userId);
            return user.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto.UserResponse> getUserByEmail(@PathVariable String email) {
        try {
            Optional<UserDto.UserResponse> user = userService.getUserByEmail(email);
            return user.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get user with their matieres
    @GetMapping("/{userId}/with-matieres")
    public ResponseEntity<UserDto.UserWithMatieresResponse> getUserWithMatieres(@PathVariable Integer userId) {
        try {
            Optional<UserDto.UserWithMatieresResponse> user = userService.getUserWithMatieres(userId);
            return user.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto.UserResponse> updateUser(
            @PathVariable Integer userId,
            @RequestBody UserDto.UpdateUserRequest request) {
        try {
            Optional<UserDto.UserResponse> updatedUser = userService.updateUser(userId, request);
            return updatedUser.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        try {
            boolean deleted = userService.deleteUser(userId);
            return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Search users by name (firstname or lastname)
    @GetMapping("/search")
    public ResponseEntity<List<UserDto.UserResponse>> searchUsersByName(@RequestParam String name) {
        try {
            List<UserDto.UserResponse> users = userService.searchUsersByName(name);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // User authentication/login
    @PostMapping("/login")
    public ResponseEntity<UserDto.LoginResponse> authenticateUser(
            @RequestBody UserDto.LoginRequest request) {
        try {
            UserDto.LoginResponse response = userService.authenticateUser(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}