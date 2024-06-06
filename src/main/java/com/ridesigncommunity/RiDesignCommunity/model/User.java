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
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "user_name", unique = true)
    private String username;

    @Column(name = "is_ri_designer")
    private boolean isRiDesigner;

    @OneToOne(mappedBy = "user")
    private ImageData imageData;

    @OneToMany(mappedBy = "user")
    private List<Product> products;

    @ElementCollection
    @CollectionTable(name = "user_favorites", joinColumns = @JoinColumn(name = "email"))
    @Column(name = "product_id")
    private List<Long> favorites = new ArrayList<>();

    @OneToMany(
            mappedBy = "email",
            targetEntity = Authority.class,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();


    public User(String username) {

        this.username = username;

    }

    public User(String email, String password, String firstname, String lastname, String username, boolean isRiDesigner) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.isRiDesigner = isRiDesigner;
    }

    public User() {

    }

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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setRiDesigner(boolean isRiDesigner) {
        this.isRiDesigner = isRiDesigner;
    }

    public void setFavorites(List<Long> favorites) {
        this.favorites = favorites;
    }

    public Set<Authority> getAuthorities() { return authorities; }

    public void setAuthorities(Set<Authority> authorities) { this.authorities = authorities; }

    public ImageData getImageData() {
        return imageData;
    }

    public void setImageData(ImageData imageData) {
        this.imageData = imageData;
    }

    public List<Long> getFavorites() {
        return favorites;
    }
}


