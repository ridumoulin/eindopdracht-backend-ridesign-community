package com.ridesigncommunity.RiDesignCommunity.security;

import com.ridesigncommunity.RiDesignCommunity.dto.UserDto;
import com.ridesigncommunity.RiDesignCommunity.model.Authority;
import com.ridesigncommunity.RiDesignCommunity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserDto userDto = userService.getUserByEmail(email);

        if (userDto == null) {
            throw new RuntimeException("User not found with email: " + email);
        }

        Collection<? extends GrantedAuthority> authorities = mapAuthorities(userDto.getAuthorities());

        return new CustomUserDetails(
                userDto.getUsername(),
                userDto.getPassword(),
                authorities
        );
    }

    private Collection<? extends GrantedAuthority> mapAuthorities(Set<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());
    }
}