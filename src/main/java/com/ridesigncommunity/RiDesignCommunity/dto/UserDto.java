package com.ridesigncommunity.RiDesignCommunity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ridesigncommunity.RiDesignCommunity.model.Authority;
import com.ridesigncommunity.RiDesignCommunity.model.Product;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Set;

public class UserDto {

    @JsonProperty("userId")
    public Long userId;

    @NotBlank
    @Email
    public String email;

    @NotBlank
    @Size(min=7, max=30)
    public String password;

    @NotBlank
    public String firstname;

    @NotBlank
    public String lastname;

    @NotBlank
    public String username;

    @NotBlank
    public boolean isRiDesigner;

    public List<Product> favorites;

    @NotEmpty
    public Set<Authority> authorities;


    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public String getFirstname() { return firstname; }

    public String getLastname() { return lastname; }

    public String getUsername() { return username; }

    public boolean isRiDesigner() { return isRiDesigner; }

    public List<Product> getFavorites() { return favorites; }

    public Set<Authority> getAuthorities() { return authorities; }


    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public void setFirstname(String firstname) { this.firstname = firstname; }

    public void setLastname(String lastname) { this.lastname = lastname; }

    public void setUsername(String username) { this.username = username; }

    public void setRiDesigner(boolean riDesigner) { isRiDesigner = riDesigner; }

    public void setFavorites(List<Product> favorites) { this.favorites = favorites; }

    public void setAuthorities(Set<Authority> authorities) { this.authorities = authorities; }
}
