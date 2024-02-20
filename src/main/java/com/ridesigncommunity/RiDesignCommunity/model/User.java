package com.ridesigncommunity.RiDesignCommunity.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "user_name")
    private String username;

    @Column(name = "is_ri_designer")
    private boolean isRiDesigner;

    @OneToOne(mappedBy = "user")
    private ImageData imageData;

    @ElementCollection
    @CollectionTable(name = "favorites", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "product_id")
    private List<Product> favorites = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            targetEntity = Authority.class,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId;}

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) { this.firstname = firstname; }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRiDesigner() {
        return isRiDesigner;
    }

    public void setRiDesigner(boolean isRiDesigner) {
        this.isRiDesigner = isRiDesigner;
    }

    public List<Product> getFavorites() { return favorites; }

    public void setFavorites(List<Product> favorites) { this.favorites = favorites; }

    public Set<Authority> getAuthorities() { return authorities; }

    public void setAuthorities(Set<Authority> authorities) { this.authorities = authorities; }

    public ImageData getImageData() {
        return imageData;
    }

    public void setImageData(ImageData imageData) {
        this.imageData = imageData;
    }
}
