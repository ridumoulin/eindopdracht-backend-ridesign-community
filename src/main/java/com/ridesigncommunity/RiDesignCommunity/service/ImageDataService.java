package com.ridesigncommunity.RiDesignCommunity.service;

import com.ridesigncommunity.RiDesignCommunity.model.ImageData;
import com.ridesigncommunity.RiDesignCommunity.repository.ImageDataRepository;
import com.ridesigncommunity.RiDesignCommunity.repository.ProductRepository;
import com.ridesigncommunity.RiDesignCommunity.utils.ImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;
import java.io.IOException;

import java.io.IOException;

@Service
public class ImageDataService {

    private final ImageDataRepository imageDataRepository;
    private final ProductRepository productRepository;

    public ImageDataService(ImageDataRepository imageDataRepository, ProductRepository productRepository) {
        this.imageDataRepository = imageDataRepository;
        this.productRepository = productRepository;
    }

    public String uploadImage(MultipartFile multipartFile, String productId) throws IOException{
        Optional<Product> product = productRepository.findById(productId);
        Product product1 = product.get();

        ImageData imageData = new ImageData();
        imageData.setName(MultipartFile.getName());
        imageData.setType(multipartFile.getContentType());
        imageData.getImageData(ImageUtil.compressImage(MultipartFile.getBytes()));
        imageData.setProduct(product1);

        ImageData savedImage = new imageRepository.save(imageData);
        product1.setImage(savedImage);
        userRepository.save(product1);
        return savedImage.getName();
    }

    public byte[] downloadImage(String productId) throws IOException {
        Optional<Product> product = productRepository.findById(productId);
        Product product1 = product.get();

        ImageData imageData = product1.getImageData();
        return ImageUtil.decompressImage(imageData.getImageData());
}
