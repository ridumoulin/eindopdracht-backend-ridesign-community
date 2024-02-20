package com.ridesigncommunity.RiDesignCommunity.controller;

import com.ridesigncommunity.RiDesignCommunity.dto.UserDto;
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

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUpUser(@Validated @RequestBody UserDto userDto) {
        boolean emailExists = userService.existsByEmail(userDto.getEmail());
        if (emailExists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email address is already registered.");
        }

        userService.signUpUser(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserDto userDto) {
        boolean loginSuccess = userService.loginUser(userDto);
        if (!loginSuccess) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password.");
        }

        return ResponseEntity.ok("Login successful.");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        Optional<UserDto> userDtoOptional = userService.getUserById(userId);
        return userDtoOptional.map(dto -> ResponseEntity.ok().body(dto))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @Validated @RequestBody UserDto userDto) {
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
}



