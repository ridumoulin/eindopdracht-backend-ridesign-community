package com.ridesigncommunity.RiDesignCommunity.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class InquiryDto {
    public Long inquiryrId;

    @NotBlank
    @Size(min=100)
    public String description;

    @NotBlank
    @Email
    public String email;

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
