package com.ridesigncommunity.RiDesignCommunity.service;

import com.ridesigncommunity.RiDesignCommunity.dto.UserInputDto;
import com.ridesigncommunity.RiDesignCommunity.dto.UserOutputDto;
import com.ridesigncommunity.RiDesignCommunity.model.Authority;
import com.ridesigncommunity.RiDesignCommunity.model.ImageData;
import com.ridesigncommunity.RiDesignCommunity.model.User;
import com.ridesigncommunity.RiDesignCommunity.repository.UserRepository;
import com.ridesigncommunity.RiDesignCommunity.utils.ImageUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductService productService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ProductService productService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.productService = productService;
    }

    @Transactional
    public List<UserOutputDto> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public void registerUser(UserInputDto userDto) {
        validateUserInput(userDto);

        if (userRepository.existsById(userDto.getEmail())) {
            throw new IllegalArgumentException("Email address is already registered.");
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setUsername(userDto.getUsername());
        user.setRiDesigner(userDto.isRiDesigner());


        Set<Authority> setAuth = new HashSet<>();
        Authority a = new Authority();
        a.setAuthority("ROLE_USER");
        a.setEmail(userDto.getEmail());
        setAuth.add(a);
        user.setAuthorities(setAuth);


        userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsById(email);
    }

    public boolean authenticateUser(UserInputDto userDto) {
        Optional<User> oUser = userRepository.findById(userDto.getEmail());

        if (oUser == null) {
            return false;
        }

        return passwordEncoder.matches(userDto.getPassword(), oUser.get().getPassword());
    }

    @Transactional
    public UserOutputDto getUserById(String username) {
        Optional<User> userOptional = userRepository.findById(username);
        return convertToDto(userOptional.get());
    }

    @Transactional
    public boolean updateUsername(String email, String newUsername) {
        Optional<User> userOptional = userRepository.findById(email);
        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();
        user.setUsername(newUsername);
        userRepository.save(user);
        return true;
    }

    @Transactional
    public boolean deleteUser(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return false;
        }

        userRepository.delete(userOptional.get());
        return true;
    }


    public UserOutputDto getUserByEmail(String email) {
        Optional<User> oUser = userRepository.findById(email);
        if (oUser.isPresent()){
        return convertToDto(oUser.get());
        }
        else {
            return null;
        }
    }

    @Transactional
    public boolean addFavorite(String username, Long productId) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();
        List<Long> favorites = user.getFavorites();
        if (!favorites.contains(productId)) {
            favorites.add(productId);
            user.setFavorites(favorites);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean removeFavorite(String username, Long productId) {
        Optional<User> userOptional = userRepository.findByUsername(username);
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

    public UserOutputDto convertToDto(User user) {
        UserOutputDto userDto = new UserOutputDto();
        userDto.setEmail(user.getEmail());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setUsername(user.getUsername());
        userDto.setRiDesigner(user.isRiDesigner());
        if(user.getImageData() != null){
            userDto.setImageData(ImageUtil.decompressImage(user.getImageData().getImageData()));
        }

        userDto.setAuthorities(user.getAuthorities());
        userDto.setFavorites(user.getFavorites());
        if (user.getProducts() != null){
            userDto.setProducts(productService.productDtoList(user.getProducts()));
        }

        return userDto;
    }


    public void validateUserInput(UserInputDto userDto) {
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
