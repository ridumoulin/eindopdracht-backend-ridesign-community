package com.ridesigncommunity.RiDesignCommunity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ridesigncommunity.RiDesignCommunity.model.Authority;
import com.ridesigncommunity.RiDesignCommunity.model.ImageData;
import com.ridesigncommunity.RiDesignCommunity.model.Product;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserInputDto {

    @JsonProperty("userId")
    public Long userId;

    @NotBlank(message = "E-mail is required")
    @Email
    public String email;

    @NotBlank(message = "Password is required")
    @Size(min=8, max=30)
    public String password;

    @NotBlank(message = "Firstname is required")
    public String firstname;

    @NotBlank(message = "Lastname is required")
    public String lastname;

    @NotBlank(message = "Username is required")
    public String username;

    public boolean isRiDesigner;

    private List<Long> favorites = new ArrayList<>();

    public UserInputDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserInputDto(String email, String password, String firstname, String lastname, String username, boolean isRiDesigner) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.isRiDesigner = isRiDesigner;
    }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public String getFirstname() { return firstname; }

    public String getLastname() { return lastname; }

    public String getUsername() { return username; }

    public boolean isRiDesigner() { return isRiDesigner; }

    public List<Long> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Long> favorites) {
        this.favorites = favorites;
    }

    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public void setFirstname(String firstname) { this.firstname = firstname; }

    public void setLastname(String lastname) { this.lastname = lastname; }

    public void setUsername(String username) { this.username = username; }

    public void setRiDesigner(boolean riDesigner) { isRiDesigner = riDesigner; }


    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
