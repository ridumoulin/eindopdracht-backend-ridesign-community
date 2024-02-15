package com.ridesigncommunity.RiDesignCommunity.model;

import jakarta.persistence.*;

import java.io.File;
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

    @OneToOne(mappedBy = "product")
    private ImageData imageData;

    @Column(name = "category")
    private String category;

    @Column(name = "measurements")
    private String measurements;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @ElementCollection
    @CollectionTable(name = "delivery_options", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "delivery_option")
    private List<String> deliveryOptions = new ArrayList<>();


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

    public ImageData getImageData() {
        return imageData;
    }

    public void setImageData(ImageData imageData) {
        this.imageData = imageData;
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

    public ImageData getImageData() {
        return imageData;
    }
}
