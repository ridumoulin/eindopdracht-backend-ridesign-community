package com.ridesigncommunity.RiDesignCommunity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ridesigncommunity.RiDesignCommunity.model.ImageData;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class ProductDto {

    @JsonProperty("userId")
    public Long productId;

    @NotBlank
    public String productTitle;

    @NotBlank
    public List<ImageData> images = new ArrayList<>();

    @NotBlank
    public String category;

    @NotBlank
    public String measurements;

    @NotBlank
    private String materials;

    @NotBlank
    @Size(min=30)
    public String description;

    @NotBlank
    public double price;

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

    public List<ImageData> getImages() {
        return images;
    }

    public void setImages(List<ImageData> images) {
        this.images = images;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }
}
