package com.ridesigncommunity.RiDesignCommunity.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;


@Entity
@Table(name = "image_data")
public class ImageData {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String type;

    @Lob
    private byte[] imageData;

    @ManyToOne
    @JsonIgnore
//    @JoinColumn(name = "product_id", referencedColumnName = "images")
    private Product product;

    @OneToOne
    @JsonIgnore
//    @JoinColumn(name = "user_id", referencedColumnName = "image_data")
    private User user;

    public ImageData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
