package com.ridesigncommunity.RiDesignCommunity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ridesigncommunity.RiDesignCommunity.model.ImageData;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class ProductDto {

    @JsonProperty("productId")
    private Long productId;

    @NotBlank
    private String productTitle;

    @NotBlank
    private List<byte[]> images = new ArrayList<>();

    @NotBlank
    private String category;

    @NotBlank
    private String measurements;

    @NotBlank
    private String materials;

    @NotBlank
    @Size(min=30)
    private String description;

    @NotBlank
    private double price;

    private String username;

    public List<String> deliveryOptions = new ArrayList<>();

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

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
