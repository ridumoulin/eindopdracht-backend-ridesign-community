package com.ridesigncommunity.RiDesignCommunity.service;

import com.ridesigncommunity.RiDesignCommunity.dto.UserInputDto;
import com.ridesigncommunity.RiDesignCommunity.dto.UserOutputDto;
import com.ridesigncommunity.RiDesignCommunity.model.User;
import com.ridesigncommunity.RiDesignCommunity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserInputDto userDto) {
        validateUserInput(userDto);

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email address is already registered.");
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setUsername(userDto.getUsername());
        user.setRiDesigner(userDto.isRiDesigner());

        userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean authenticateUser(UserInputDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());

        if (user == null) {
            return false;
        }

        return passwordEncoder.matches(userDto.getPassword(), user.getPassword());
    }

    public Optional<UserOutputDto> getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(this::convertToDto);
    }

    public boolean updateUser(Long userId, UserInputDto userDto) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setUsername(userDto.getUsername());
        user.setRiDesigner(userDto.isRiDesigner());

        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return false;
        }

        userRepository.delete(userOptional.get());
        return true;
    }

    public UserOutputDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user != null ? convertToDto(user) : null;
    }

    public boolean addFavorite(Long userId, Long productId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();
        List<Long> favorites = user.getFavorites();
        if (!favorites.contains(productId)) {
            favorites.add(productId);
            user.setFavorites(favorites);
            userRepository.save(user);
        }
        return true;
    }

    public boolean removeFavorite(Long userId, Long productId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();
        List<Long> favorites = user.getFavorites();
        if (favorites.contains(productId)) {
            favorites.remove(productId);
            user.setFavorites(favorites);
            userRepository.save(user);
        }
        return true;
    }

    private UserOutputDto convertToDto(User user) {
        UserOutputDto userDto = new UserOutputDto();
        userDto.setEmail(user.getEmail());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setUsername(user.getUsername());
        userDto.setRiDesigner(user.isRiDesigner());

        return userDto;
    }

    private void validateUserInput(UserInputDto userDto) {
        if (userDto.getUsername() == null || userDto.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }
        if (userDto.getFirstname() == null || userDto.getFirstname().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty.");
        }
        if (userDto.getLastname() == null || userDto.getLastname().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty.");
        }
    }
}
