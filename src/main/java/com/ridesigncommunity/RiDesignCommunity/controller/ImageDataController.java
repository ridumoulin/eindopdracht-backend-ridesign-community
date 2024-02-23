package com.ridesigncommunity.RiDesignCommunity.controller;

import com.ridesigncommunity.RiDesignCommunity.model.Product;
import com.ridesigncommunity.RiDesignCommunity.model.User;
import com.ridesigncommunity.RiDesignCommunity.repository.ImageDataRepository;
import com.ridesigncommunity.RiDesignCommunity.model.ImageData;
import com.ridesigncommunity.RiDesignCommunity.repository.ProductRepository;
import com.ridesigncommunity.RiDesignCommunity.repository.UserRepository;
import com.ridesigncommunity.RiDesignCommunity.service.ImageDataService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/image")
public class ImageDataController {

    private final ImageDataService imageDataService;
    private final ImageDataRepository imageDataRepository;

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ImageDataController(ImageDataService imageDataService, ImageDataRepository imageDataRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.imageDataService = imageDataService;
        this.imageDataRepository = imageDataRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<Object> uploadImages(@RequestParam("file") MultipartFile multipartFile, @PathVariable Long productId) throws IOException {
        String image = imageDataService.uploadImages(multipartFile, productId);
        return ResponseEntity.ok("File has been uploaded for product with ID " + productId + ": " + image);
    }

    @PostMapping("/user/{username}")
    public ResponseEntity<Object> uploadImage(@RequestParam("file") MultipartFile multipartFile, @PathVariable String username) throws IOException {
        String image = imageDataService.uploadImage(multipartFile, username);
        return ResponseEntity.ok("File has been uploaded for user with ID " + username + ": " + image);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Object> downloadImages(@PathVariable Long productId) throws IOException {
        List<byte[]> images = imageDataService.downloadImages(productId);
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            List<MediaType> mediaTypes = new ArrayList<>();
            List<ImageData> dbImageDatas = product.get().getImages();
            for (ImageData imageData : dbImageDatas) {
                mediaTypes.add(MediaType.valueOf(imageData.getType()));
            }
            if (!images.isEmpty()) {
                return ResponseEntity.ok().contentType(mediaTypes.get(0)).body(images.get(0));
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Object> downloadImage(@PathVariable String username) throws IOException {
        byte[] image = imageDataService.downloadImage(username);
        Optional<User> user = userRepository.findById(username);
        Optional<ImageData> dbImageData = imageDataRepository.findById(user.get().getImageData().getId());
        MediaType mediaType = MediaType.valueOf(dbImageData.get().getType());
        return ResponseEntity.ok().contentType(mediaType).body(image);
    }
}
