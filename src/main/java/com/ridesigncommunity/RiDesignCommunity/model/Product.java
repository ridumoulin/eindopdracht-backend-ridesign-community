package com.ridesigncommunity.RiDesignCommunity.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "product_title")
    private String productTitle;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ImageData> images = new ArrayList<>();

    @Column(name = "category")
    private String category;

    @Column(name = "measurements")
    private String measurements;

    @Column(name = "materials")
    private String materials;

    @Column(name = "description", length = 10000)
    private String description;

    @Column(name = "price")
    private double price;

    @ElementCollection
    @CollectionTable(name = "delivery_options", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "delivery_options")
    private List<String> deliveryOptions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "email")
    private User user;

    public Product(Long productId, String productTitle, List<ImageData> images, String category, String measurements, String materials, String description, double price, List<String> deliveryOptions, User user) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.images = images;
        this.category = category;
        this.measurements = measurements;
        this.materials = materials;
        this.description = description;
        this.price = price;
        this.deliveryOptions = deliveryOptions;
        this.user = user;
    }

    public Product() {

    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public List<ImageData> getImages() {
        return images;
    }

    public void setImages(List<ImageData> images) {
        this.images = images;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMeasurements() {
        return measurements;
    }

    public void setMeasurements(String measurements) {
        this.measurements = measurements;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getDeliveryOptions() {
        return deliveryOptions;
    }

    public void setDeliveryOptions(List<String> deliveryOptions) {
        this.deliveryOptions = deliveryOptions;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}

