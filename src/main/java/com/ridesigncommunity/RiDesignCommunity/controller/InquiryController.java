package com.ridesigncommunity.RiDesignCommunity.controller;

import com.ridesigncommunity.RiDesignCommunity.dto.InquiryDto;
import com.ridesigncommunity.RiDesignCommunity.model.Inquiry;
import com.ridesigncommunity.RiDesignCommunity.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inquiries")
public class InquiryController {

    private final InquiryService inquiryService;

    @Autowired
    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @PostMapping
    public ResponseEntity<InquiryDto> createInquiry(@RequestBody InquiryDto inquiryDto) {
        InquiryDto createdInquiryDto = inquiryService.createInquiry(inquiryDto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/")
                .buildAndExpand(createdInquiryDto.getInquiryId())
                .toUri();

        return ResponseEntity.created(uri).body(createdInquiryDto);
    }

    @GetMapping
    public ResponseEntity<List<InquiryDto>> getAllInquiries() {
        List<Inquiry> inquiries = inquiryService.getAllInquiries();
        List<InquiryDto> inquiryDtos = inquiries.stream()
                .map(inquiryService::mapToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(inquiryDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{inquiryId}")
    public ResponseEntity<Void> deleteInquiry(@PathVariable Long inquiryId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        try {
            inquiryService.deleteInquiry(inquiryId, authorities);
            return ResponseEntity.noContent().build();
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

