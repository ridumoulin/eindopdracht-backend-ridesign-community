package com.ridesigncommunity.RiDesignCommunity.service;

import com.ridesigncommunity.RiDesignCommunity.dto.InquiryDto;
import com.ridesigncommunity.RiDesignCommunity.model.Inquiry;
import com.ridesigncommunity.RiDesignCommunity.repository.InquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;


import java.util.Collection;
import java.util.List;


@Service
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    @Autowired
    public InquiryService(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    public InquiryDto createInquiry(InquiryDto inquiryDto) {
        try {
            validateInquiryDto(inquiryDto);

            Inquiry inquiry = new Inquiry();
            inquiry.setInquiryType(inquiryDto.getInquiryType());
            inquiry.setDescription(inquiryDto.getDescription());
            inquiry.setEmail(inquiryDto.getEmail());

            Inquiry savedInquiry = inquiryRepository.save(inquiry);

            return mapToDto(savedInquiry);
        } catch (DataAccessException ex) {
            throw new RuntimeException("Failed to create inquiry: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new RuntimeException("An unexpected error occurred: " + ex.getMessage(), ex);
        }
    }

    public InquiryDto mapToDto(Inquiry inquiry) {
        InquiryDto dto = new InquiryDto();
        dto.setInquiryId(inquiry.getInquiryId());
        dto.setInquiryType(inquiry.getInquiryType());
        dto.setDescription(inquiry.getDescription());
        dto.setEmail(inquiry.getEmail());
        return dto;
    }

    private void validateInquiryDto(InquiryDto inquiryDto) {
        if (inquiryDto.getInquiryType() == null || inquiryDto.getInquiryType().isEmpty()) {
            throw new IllegalArgumentException("Inquiry type must not be empty");
        }
        if (inquiryDto.getDescription() == null || inquiryDto.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Description must not be empty");
        }
        if (inquiryDto.getEmail() == null || inquiryDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email must not be empty");
        }
    }

    public List<Inquiry> getAllInquiries() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {

            return inquiryRepository.findAll();
        } else {
            String username = authentication.getName();
            return inquiryRepository.findByUsername(username);
        }
    }

    public void deleteInquiry(Long inquiryId, Collection<? extends GrantedAuthority> authorities) {
        boolean isAdmin = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inquiry not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (isAdmin || inquiry.getUsername().equals(username)) {
            inquiryRepository.delete(inquiry);
        } else {
            throw new AccessDeniedException("You are not authorized to delete this inquiry");
        }
    }
}