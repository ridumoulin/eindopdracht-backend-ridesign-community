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

    @GetMapping("/{username}")
    public ResponseEntity<UserOutputDto> getUserById(@PathVariable String username) {
        Optional<UserOutputDto> userDtoOptional = userService.getUserById(username);
        return userDtoOptional.map(dto -> ResponseEntity.ok().body(dto))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{username}")
    public ResponseEntity<String> updateUser(@PathVariable String username, @Validated @RequestBody UserInputDto userDto) {
        boolean updateSuccess = userService.updateUser(username, userDto);
        if (!updateSuccess) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        return ResponseEntity.ok("User profile updated successfully.");
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



