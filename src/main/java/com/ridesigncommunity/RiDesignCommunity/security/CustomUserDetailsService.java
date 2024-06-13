package com.ridesigncommunity.RiDesignCommunity.security;

import com.ridesigncommunity.RiDesignCommunity.model.Authority;
import com.ridesigncommunity.RiDesignCommunity.model.User;
import com.ridesigncommunity.RiDesignCommunity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> oUser = userRepository.findById(email);

        if (!oUser.isPresent()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        User user = oUser.get();
        Collection<? extends GrantedAuthority> authorities = mapAuthorities(user.getAuthorities());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public CustomUserDetails loadUserByEmail(String email) {
        Optional<User> oUser = userRepository.findById(email);

        if (!oUser.isPresent()) {
            throw new RuntimeException("User not found with email: " + email);
        }
        User user = oUser.get();
        Collection<? extends GrantedAuthority> authorities = mapAuthorities(user.getAuthorities());

        return new CustomUserDetails(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    private Collection<? extends GrantedAuthority> mapAuthorities(Set<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());
    }
}