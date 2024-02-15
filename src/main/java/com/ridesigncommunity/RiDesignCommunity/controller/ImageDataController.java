package com.ridesigncommunity.RiDesignCommunity.controller;

import com.ridesigncommunity.RiDesignCommunity.repository.ImageDataRepository;
import com.ridesigncommunity.RiDesignCommunity.model.ImageData;
import com.ridesigncommunity.RiDesignCommunity.repository.ImageDataRepository;
import com.ridesigncommunity.RiDesignCommunity.repository.ProductRepository;
import com.ridesigncommunity.RiDesignCommunity.service.ImageDataService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/image")
public class ImageDataController {

    private final ImageDataService imageDataService;
    private final ImageDataRepository imageDataRepository;

    public ImageController(ImageDataService imageDataService) {
        this.imageDataService = imageDataService;
        this.imageDataRepository = imageDataRepository;
        this.productRepository = productRepository;
    }

    @PostMapping
    public ResponseEntity<Object> uploadImage(@RequestParam("file")MultipartFile multipartFile, @RequestParam String productId) throws IOException {
        String image = imageDataService.uploadImage(multipartFile, productId);
        return ResponseEntity.ok("File has been uploaded, " + image);
    }

    @GetMapping("/{producId}")
    public ResponseEntity<Object> downlaodImage(@PathVariable String productId) throws IOException {
        byte[] image = imageDataService.downloadImage(productId);
        Product product = productRepository.findById(productId);
        Optional<ImageData> dbImageData = imageDataRepository.findById(product.get().getImageData().getId());
        MediaType mediaType = MediaType.valueOf(dbImageData.get().getType());
        return ResponseEntity.ok().contentType(mediaType).body(image);
    }

}
