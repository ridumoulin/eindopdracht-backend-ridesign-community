package com.ridesigncommunity.RiDesignCommunity.model;

import jakarta.persistence.*;

@Entity
@Table(name = "inquiries")
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryId;

    @Column(name = "inquiry_type")
    private String inquiryType;

    @Column(name = "description", length = 10000)
    private String description;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    public Long getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(Long inquiryrId) {
        this.inquiryId = inquiryrId;
    }

    public String getInquiryType() {
        return inquiryType;
    }

    public void setInquiryType(String inquiryType) {
        this.inquiryType = inquiryType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
