package com.ridesigncommunity.RiDesignCommunity.model;

import jakarta.persistence.*;

@Entity
@Table(name = "inquiries")
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryrId;

    @Column(name = "description")
    private String description;

    @Column(name = "email")
    private String email;

    public Long getInquiryrId() {
        return inquiryrId;
    }

    public void setInquiryrId(Long inquiryrId) {
        this.inquiryrId = inquiryrId;
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
}
