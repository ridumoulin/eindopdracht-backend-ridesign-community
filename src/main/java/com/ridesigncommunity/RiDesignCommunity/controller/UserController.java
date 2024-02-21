package com.ridesigncommunity.RiDesignCommunity.controller;

import com.ridesigncommunity.RiDesignCommunity.dto.UserInputDto;
import com.ridesigncommunity.RiDesignCommunity.dto.UserOutputDto;
import com.ridesigncommunity.RiDesignCommunity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Validated @RequestBody UserInputDto userDto) {
        boolean emailExists = userService.existsByEmail(userDto.getEmail());
        if (emailExists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email address is already registered.");
        }

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userService.registerUser(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
    }


    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateUser(@RequestBody UserInputDto userDto) {
        boolean loginSuccess = userService.authenticateUser(userDto);
        if (!loginSuccess) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password.");
        }

        return ResponseEntity.ok("Login successful.");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserOutputDto> getUserById(@PathVariable Long userId) {
        Optional<UserOutputDto> userDtoOptional = userService.getUserById(userId);
        return userDtoOptional.map(dto -> ResponseEntity.ok().body(dto))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @Validated @RequestBody UserInputDto userDto) {
        boolean updateSuccess = userService.updateUser(userId, userDto);
        if (!updateSuccess) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        return ResponseEntity.ok("User profile updated successfully.");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        boolean deleteSuccess = userService.deleteUser(userId);
        if (!deleteSuccess) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/addFavorite/{userId}/{productId}")
    public ResponseEntity<String> addFavorite(@PathVariable Long userId, @PathVariable Long productId) {
        boolean success = userService.addFavorite(userId, productId);
        if (!success) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Product added to favorites successfully.");
    }

    @DeleteMapping("/removeFavorite/{userId}/{productId}")
    public ResponseEntity<String> removeFavorite(@PathVariable Long userId, @PathVariable Long productId) {
        boolean success = userService.removeFavorite(userId, productId);
        if (!success) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Product removed from favorites successfully.");
    }
}



