package com.ridesigncommunity.RiDesignCommunity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class InquiryDto {
    public Long inquiryId;

    @NotBlank
    public String inquiryType;

    @NotBlank
    @Size(min=100)
    public String description;

    @NotBlank
    @Email
    public String email;

    public Long getInquiryrId() {
        return inquiryId;
    }

    public void setInquiryrId(Long inquiryrId) {
        this.inquiryId = inquiryrId;
    }

    public Long getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(Long inquiryId) {
        this.inquiryId = inquiryId;
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
}
