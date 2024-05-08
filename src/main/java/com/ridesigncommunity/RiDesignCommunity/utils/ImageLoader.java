package com.ridesigncommunity.RiDesignCommunity.utils;

import com.ridesigncommunity.RiDesignCommunity.model.ImageData;
import com.ridesigncommunity.RiDesignCommunity.model.Product;
import com.ridesigncommunity.RiDesignCommunity.repository.ImageDataRepository;
import com.ridesigncommunity.RiDesignCommunity.repository.ProductRepository;
import com.ridesigncommunity.RiDesignCommunity.service.ImageDataService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Component
public class ImageLoader implements ApplicationListener<ApplicationReadyEvent> {

    private final ImageDataService imageDataService;
    private final ProductRepository productRepository;
    private final ImageDataRepository imgdataRepos;
    private final Map<Long, String[]> productIdToImageFiles = new HashMap<>();
    private final Map<String, String> usernameToImageFile = new HashMap<>();

    @Autowired
    public ImageLoader(ImageDataService imageDataService, ProductRepository productRepository, ImageDataRepository imgdataRepos) {
        this.imageDataService = imageDataService;
        this.productRepository = productRepository;
        this.imgdataRepos = imgdataRepos;
        initializeProductIdToImageFiles();
        initializeUsernameToImageFile();
    }

    public void loadImages() throws IOException {
        for (Map.Entry<Long, String[]> entry : productIdToImageFiles.entrySet()) {
            Long productId = entry.getKey();
            String[] imageFiles = entry.getValue();
            for (String imageFile : imageFiles) {
                ImageData imgdata = new ImageData();
                imgdata.setImageData(ImageUtil.compressImage(Files.readAllBytes(Paths.get("src/main/resources/images/products/" + imageFile))));
                imgdata.setType("image/" + imageFile.substring(imageFile.lastIndexOf(".")));
                Product p = new Product();
                        p.setProductId(productId);
                imgdata.setProduct(p);
                imgdata.setName(imageFile);
                imgdataRepos.save(imgdata);

            }
        }
    }

    private void initializeProductIdToImageFiles() {
        productIdToImageFiles.put(1L, new String[]{"photo-tables-1.jpeg","photo-tables-2.jpeg", "photo-tables-3.jpeg"});
        productIdToImageFiles.put(2L, new String[]{"photo-chair-olaf-1.jpeg", "photo-chair-olaf-2.jpeg", "photo-chair-olaf-3.jpeg"});
        productIdToImageFiles.put(3L, new String[]{"photo-tables-ri-1.jpeg", "photo-tables-ri-2.jpeg", "photo-tables-ri-3.jpeg"});
        productIdToImageFiles.put(4L, new String[]{"photo-chair-ri-1.jpeg", "photo-chair-ri-2.jpeg", "photo-chair-ri-3.jpeg"});
        productIdToImageFiles.put(5L, new String[]{"photo-bed-1.jpeg", "photo-bed-2.jpeg", "photo-bed-3.jpeg"});
        productIdToImageFiles.put(6L, new String[]{"photo-closet-1.jpeg", "photo-closet-2.jpeg", "photo-closet-3.jpeg"});
    }


    private void initializeUsernameToImageFile() {
        usernameToImageFile.put("RiDumoulin", "photo-profile-ri.jpeg");
        usernameToImageFile.put("Brolaf", "photo-profile-olaf.jpeg");
        usernameToImageFile.put("CHDesigner", "photo-profile-catrina.jpeg");
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            loadImages();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}