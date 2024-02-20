package com.ridesigncommunity.RiDesignCommunity.model;

import jakarta.persistence.*;
import com.ridesigncommunity.RiDesignCommunity.model.Product;

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

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
