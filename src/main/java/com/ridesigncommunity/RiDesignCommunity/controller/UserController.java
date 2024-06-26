package com.ridesigncommunity.RiDesignCommunity.controller;

import com.ridesigncommunity.RiDesignCommunity.dto.UserInputDto;
import com.ridesigncommunity.RiDesignCommunity.dto.UserOutputDto;
import com.ridesigncommunity.RiDesignCommunity.dto.UsernameUpdateDto;
import com.ridesigncommunity.RiDesignCommunity.model.Product;
import com.ridesigncommunity.RiDesignCommunity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;
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

    @GetMapping
    public ResponseEntity<List<UserOutputDto>> getAllUsers() {
        List<UserOutputDto> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Validated @RequestBody UserInputDto userDto) {
        boolean emailExists = userService.existsByEmail(userDto.getEmail());
        if (emailExists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email address is already registered.");
        }
        userService.registerUser(userDto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/register")
                .build()
                .toUri();

        return ResponseEntity.created(uri).body("User registered successfully.");
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserOutputDto> getUserById(@PathVariable String username) {
        UserOutputDto userDtoOptional = userService.getUserById(username);
    return ResponseEntity.ok(userDtoOptional);
    }

    @PutMapping("/{email}")
    public ResponseEntity<String> updateUsername(@PathVariable String email, @Validated @RequestBody UsernameUpdateDto usernameUpdateDto) {
        boolean updateSuccess = userService.updateUsername(email, usernameUpdateDto.getUsername());
        if (!updateSuccess) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        return ResponseEntity.ok("Username updated successfully.");
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        boolean deleteSuccess = userService.deleteUser(username);
        if (!deleteSuccess) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/addFavorite/{username}/{productId}")
    public ResponseEntity<String> addFavorite(@PathVariable String username, @PathVariable Long productId) {
        boolean success = userService.addFavorite(username, productId);
        if (!success) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Product added to favorites successfully.");
    }

    @DeleteMapping("/removeFavorite/{username}/{productId}")
    public ResponseEntity<String> removeFavorite(@PathVariable String username, @PathVariable Long productId) {
        boolean success = userService.removeFavorite(username, productId);
        if (!success) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Product removed from favorites successfully.");
    }
}



