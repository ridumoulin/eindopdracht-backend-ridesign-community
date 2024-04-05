package com.ridesigncommunity.RiDesignCommunity.controller;

import com.ridesigncommunity.RiDesignCommunity.dto.AuthenticationRequest;
import com.ridesigncommunity.RiDesignCommunity.dto.AuthenticationResponse;
import com.ridesigncommunity.RiDesignCommunity.security.CustomUserDetails;
import com.ridesigncommunity.RiDesignCommunity.security.CustomUserDetailsService;
import com.ridesigncommunity.RiDesignCommunity.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    public AuthenticationController(
            AuthenticationManager authenticationManager,
            CustomUserDetailsService customUserDetailsService,
            JwtUtil jwtUtil
    ) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping(value = "/authenticated")
    public ResponseEntity<Object> authenticated(Authentication authentication, Principal principal) {
        return ResponseEntity.ok().body(principal);
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        String email = authenticationRequest.getEmail();
        String password = authenticationRequest.getPassword();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (BadCredentialsException exception) {
            throw new Exception("Incorrect username or password", exception);
        }

        final CustomUserDetails customUserDetails = customUserDetailsService.loadUserByEmail(email);

        final String jwt = jwtUtil.generateToken(email);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}