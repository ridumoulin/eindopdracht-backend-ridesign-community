package com.ridesigncommunity.RiDesignCommunity.dto;
import com.ridesigncommunity.RiDesignCommunity.model.Product;
import com.ridesigncommunity.RiDesignCommunity.model.Authority;
import com.ridesigncommunity.RiDesignCommunity.model.ImageData;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserOutputDto {

        @JsonProperty("userId")
        private Long userId;

        private String email;
        private String firstname;
        private String lastname;
        private String username;
        private boolean isRiDesigner;

        private List<Long> favorites = new ArrayList<>();

        private byte[] imageData;

        private Set<Authority> authorities = new HashSet<>();

        private List<ProductDto> products = new ArrayList<>();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isRiDesigner() {
        return isRiDesigner;
    }

    public void setRiDesigner(boolean riDesigner) {
        isRiDesigner = riDesigner;
    }

//    public ImageData getImageData() {
//        return imageData;
//    }
//
//    public void setImageData(ImageData imageData) {
//        this.imageData = imageData;
//    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public List<Long> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Long> favorites) {
        this.favorites = favorites;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }

}
