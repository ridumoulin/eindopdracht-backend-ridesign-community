package com.ridesigncommunity.RiDesignCommunity.service;

import com.ridesigncommunity.RiDesignCommunity.model.ImageData;
import com.ridesigncommunity.RiDesignCommunity.model.User;
import com.ridesigncommunity.RiDesignCommunity.repository.ImageDataRepository;
import com.ridesigncommunity.RiDesignCommunity.repository.ProductRepository;
import com.ridesigncommunity.RiDesignCommunity.model.Product;
import com.ridesigncommunity.RiDesignCommunity.repository.UserRepository;
import com.ridesigncommunity.RiDesignCommunity.utils.ImageUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.io.IOException;

@Service
public class ImageDataService {

    private final ImageDataRepository imageDataRepository;
    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    public ImageDataService(ImageDataRepository imageDataRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.imageDataRepository = imageDataRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public String uploadImages(MultipartFile multipartFile, Long productId) throws IOException {
        Optional<Product> productOptional = productRepository.findById(productId);
        Product product = productOptional.orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        ImageData imageData = new ImageData();
        imageData.setName(multipartFile.getOriginalFilename());
        imageData.setType(multipartFile.getContentType());
        imageData.setImageData(ImageUtil.compressImage(multipartFile.getBytes()));
        imageData.setProduct(product);

        ImageData savedImage = imageDataRepository.save(imageData);

        product.getImages().add(savedImage);
        productRepository.save(product);

        return savedImage.getName();
    }

    public String uploadImage(MultipartFile multipartFile, String username) throws IOException {
        Optional<User> userOptional = userRepository.findById(username);
        User user = userOptional.orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + username));

        ImageData imageData = new ImageData();
        imageData.setName(multipartFile.getOriginalFilename());
        imageData.setType(multipartFile.getContentType());
        imageData.setImageData(ImageUtil.compressImage(multipartFile.getBytes()));
        imageData.setUser(user);

        ImageData savedImage = imageDataRepository.save(imageData);

        user.setImageData(savedImage);
        userRepository.save(user);

        return savedImage.getName();
    }


    @Transactional
    public byte[] downloadImage(String username) throws IOException {
        Optional<User> user = userRepository.findById(username);
        User user1;
        if (user.isPresent()) {
            user1 = user.get();
            ImageData imageData = user.get().getImageData();
            if (imageData != null) {
                return ImageUtil.decompressImage(imageData.getImageData());
            }
        }

        return new byte[0];
    }

    @Transactional
    public List<byte[]> downloadImages(Long productId) throws IOException {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            List<byte[]> imagesData = new ArrayList<>();
            Product product = productOptional.get();
            for (ImageData imageData : product.getImages()) {
                byte[] imageDataBytes = ImageUtil.decompressImage(imageData.getImageData());
                imagesData.add(imageDataBytes);
            }
            return imagesData;
        } else {
            return Collections.emptyList();
        }
    }
}
