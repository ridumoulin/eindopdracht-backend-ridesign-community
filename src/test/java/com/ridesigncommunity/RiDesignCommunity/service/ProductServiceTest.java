package com.ridesigncommunity.RiDesignCommunity.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.ridesigncommunity.RiDesignCommunity.dto.ProductDto;
import com.ridesigncommunity.RiDesignCommunity.model.ImageData;
import com.ridesigncommunity.RiDesignCommunity.model.User;
import com.ridesigncommunity.RiDesignCommunity.repository.ImageDataRepository;
import com.ridesigncommunity.RiDesignCommunity.utils.ImageUtil;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import com.ridesigncommunity.RiDesignCommunity.model.Product;
import com.ridesigncommunity.RiDesignCommunity.repository.ProductRepository;
import com.ridesigncommunity.RiDesignCommunity.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private ImageDataRepository imageDataRepositoryMock;

    @Mock
    private ImageUtil imageUtilMock;

    @InjectMocks
    private ProductService productService;

    @InjectMocks
    private UserService userService;

    Product product;
    User user;

    @BeforeEach
    void init() throws IOException {
        user = new User("HillieDesign");

        ImageData savedImageData = new ImageData();
        savedImageData.setImageData(ImageUtil.compressImage(Files.readAllBytes(Paths.get("src/test/java/resources/photo-profile-catrina.jpeg"))));
        savedImageData.setType("image/jpeg");

        ImageData imageData1 = new ImageData();
        imageData1.setImageData(ImageUtil.compressImage(Files.readAllBytes(Paths.get("src/test/java/resources/photo-profile-catrina.jpeg"))));
        imageData1.setType("image/jpeg");
        user.setImageData(imageData1);

        ImageData imageData2 = new ImageData();
        imageData2.setImageData(ImageUtil.compressImage(Files.readAllBytes(Paths.get("src/test/java/resources/photo-profile-catrina.jpeg"))));
        imageData2.setType("image/jpeg");
        user.setImageData(imageData2);

        ImageData imageData3 = new ImageData();

        imageData3.setImageData(ImageUtil.compressImage(Files.readAllBytes(Paths.get("src/test/java/resources/photo-profile-catrina.jpeg"))));
        imageData3.setType("image/jpeg");
        user.setImageData(imageData3);
        List<ImageData> images = List.of(imageData1, imageData2, imageData3);

        product = new Product(1L, "Bank in nieuw jasje", images, "Banken", "2000cm x 80cm x 100cm", "Hout en textiel", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.", 399.99, List.of("Bezorgen", "Ophalen"), user);

        imageData1.setProduct(product);
        imageData2.setProduct(product);
        imageData3.setProduct(product);
        product.setUser(user);

        byte[] compressedImageData = new byte[]{4, 5, 6};
    }

    @AfterEach
    void tearDown() {
        product = null;
        user = null;
    }

    @Test
    @DisplayName("Should successfully create product")
    void createProduct() {

        ProductDto productDto = new ProductDto();
        productDto.setProductTitle("Bank in nieuw jasje");
        productDto.setCategory("Banken");
        productDto.setMeasurements("2000cm x 80cm x 100cm");
        productDto.setMaterials("Hout en textiel");
        productDto.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.");
        productDto.setPrice(399.99);
        productDto.setDeliveryOptions(List.of("Bezorgen", "Ophalen"));
        productDto.setUsername("HillieDesign");

        when(userRepositoryMock.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(productRepositoryMock.save(any(Product.class))).thenReturn(product);

        ProductDto createdProduct = productService.createProduct(productDto);

        assertNotNull(createdProduct);
        assertEquals(product.getProductId(), createdProduct.getProductId());
        assertEquals(product.getProductTitle(), createdProduct.getProductTitle());
        verify(productRepositoryMock, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Should get all products")
    void getAllProducts() {

        when(productRepositoryMock.findAll()).thenReturn(List.of(product));

        List<ProductDto> result = productService.getAllProducts();

        assertEquals(1L, result.get(0).getProductId());
        assertEquals("Bank in nieuw jasje", result.get(0).getProductTitle());
        assertEquals(product.getCategory(), result.get(0).getCategory());
        assertEquals(product.getMeasurements(), result.get(0).getMeasurements());
        assertEquals(product.getMaterials(), result.get(0).getMaterials());
        assertEquals(product.getDescription(), result.get(0).getDescription());
        assertEquals(product.getPrice(), result.get(0).getPrice());
        assertEquals(product.getDeliveryOptions(), result.get(0).getDeliveryOptions());
    }


    @Test
    @DisplayName("Can delete product")
    void deleteProduct() {

        when(productRepositoryMock.getReferenceById(anyLong())).thenReturn(product);

        productService.deleteProduct(1L);

        verify(productRepositoryMock, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existing product")
    void deleteNonExistingProduct_ShouldThrowEntityNotFoundException() {

        when(productRepositoryMock.getReferenceById(anyLong())).thenReturn(null);


        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> productService.deleteProduct(7L));
        assertEquals("Product not found with ID: 7", exception.getMessage());

        verify(productRepositoryMock, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Should get products by category")
    void getProductsByCategory() {

        String category = "Banken";
        when(productRepositoryMock.findByCategoryIgnoreCase(category)).thenReturn(List.of(product));

        List<ProductDto> result = productService.getProductsByCategory(category);

        assertEquals(1, result.size());
        assertEquals("Bank in nieuw jasje", result.get(0).getProductTitle());
    }

    @Test
    @DisplayName("Should get products by username")
    void getProductsByUsername() {

        String username = "HillieDesign";
        when(userRepositoryMock.existsById(username)).thenReturn(true);
        when(productRepositoryMock.findProductByUser_Username(username)).thenReturn(List.of(product));

        List<Product> result = productService.getProductsByUsername(username);

        assertEquals(1, result.size());
        assertEquals("Bank in nieuw jasje", result.get(0).getProductTitle());
    }

    @Test
    @DisplayName("Should throw exception when user does not exist")
    void getProductsByNonExistingUsername() {
        String nonExistingUsername = "HellieBellie";
        when(userRepositoryMock.existsById(nonExistingUsername)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> productService.getProductsByUsername(nonExistingUsername));
    }

    @Test
    @DisplayName("Should get product by Id")
    void getProductById() {

        Long productId = 1L;
        when(productRepositoryMock.getById(productId)).thenReturn(product);

        ProductDto result = productService.getProductById(productId);

        assertNotNull(result);
        assertEquals("Bank in nieuw jasje", result.getProductTitle());
    }

    @Test
    @DisplayName("Should search products by category and product title")
    void searchProducts() {

        String category = "Banken";
        String productTitle = "Bank";
        when(productRepositoryMock.findByCategoryAndProductTitleContainingIgnoreCase(category, productTitle)).thenReturn(List.of(product));

        List<ProductDto> result = productService.searchProducts(category, productTitle);

        assertEquals(1, result.size());
        assertEquals("Bank in nieuw jasje", result.get(0).getProductTitle());
    }

    @Test
    @DisplayName("Should search products by category only")
    void searchProductsByCategory() {

        String category = "Banken";
        when(productRepositoryMock.findByCategoryIgnoreCase(category)).thenReturn(List.of(product));

        List<ProductDto> result = productService.searchProducts(category, null);

        assertEquals(1, result.size());
        assertEquals("Bank in nieuw jasje", result.get(0).getProductTitle());
    }

    @Test
    @DisplayName("Should search products by product title only")
    void searchProductsByProductTitleOnly() {
        String productTitle = "Bank";
        when(productRepositoryMock.findByProductTitleContainingIgnoreCase(productTitle)).thenReturn(List.of(product));

        List<ProductDto> result = productService.searchProducts(null, productTitle);

        assertEquals(1, result.size());
        assertEquals("Bank in nieuw jasje", result.get(0).getProductTitle());
    }

    @Test
    @DisplayName("Should search all products when both category and product title are null")
    void searchAllProductsWhenBothCategoryAndProductTitleAreNull() {
        when(productRepositoryMock.findAll()).thenReturn(List.of(product));

        List<ProductDto> result = productService.searchProducts(null, null);

        assertEquals(1, result.size());
        assertEquals("Bank in nieuw jasje", result.get(0).getProductTitle());
    }

    @Test
    @DisplayName("Should throw exception when product title is null")
    void createProductWithNullTitle() {

        ProductDto productDto = new ProductDto();
        productDto.setCategory("Banken");
        productDto.setMeasurements("2000cm x 80cm x 100cm");
        productDto.setMaterials("Hout en textiel");
        productDto.setDescription("Description");
        productDto.setPrice(399.99);
        productDto.setDeliveryOptions(List.of("Bezorgen", "Ophalen"));
        productDto.setUsername("HillieDesign");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> productService.createProduct(productDto));
        assertEquals("Product title must not be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when category is null")
    void createProductWithNullCategory() {

        ProductDto productDto = new ProductDto();
        productDto.setProductTitle("Bank in nieuw jasje");
        productDto.setMeasurements("2000cm x 80cm x 100cm");
        productDto.setMaterials("Hout en textiel");
        productDto.setDescription("Description");
        productDto.setPrice(399.99);
        productDto.setDeliveryOptions(List.of("Bezorgen", "Ophalen"));
        productDto.setUsername("HillieDesign");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> productService.createProduct(productDto));
        assertEquals("Category must not be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when description is null")
    void createProductWithNullDescription() {

        ProductDto productDto = new ProductDto();
        productDto.setProductTitle("Bank in nieuw jasje");
        productDto.setCategory("Banken");
        productDto.setMeasurements("2000cm x 80cm x 100cm");
        productDto.setMaterials("Hout en textiel");
        productDto.setPrice(399.99);
        productDto.setDeliveryOptions(List.of("Bezorgen", "Ophalen"));
        productDto.setUsername("HillieDesign");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> productService.createProduct(productDto));
        assertEquals("Description must not be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when price is zero or less")
    void createProductWithZeroPrice() {

        ProductDto productDto = new ProductDto();
        productDto.setProductTitle("Bank in nieuw jasje");
        productDto.setCategory("Banken");
        productDto.setMeasurements("2000cm x 80cm x 100cm");
        productDto.setMaterials("Hout en textiel");
        productDto.setDescription("Description");
        productDto.setPrice(0);
        productDto.setDeliveryOptions(List.of("Bezorgen", "Ophalen"));
        productDto.setUsername("HillieDesign");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> productService.createProduct(productDto));
        assertEquals("Price must be greater than zero", exception.getMessage());
    }

    @Test
    @DisplayName("Should convert product to ProductDto")
    void convertProductToProductDto() {

        ProductDto productDto = productService.fromModelToProductDto(product);

        assertNotNull(productDto);
        assertEquals(product.getProductId(), productDto.getProductId());
        assertEquals(product.getProductTitle(), productDto.getProductTitle());
        assertEquals(product.getCategory(), productDto.getCategory());
        assertEquals(product.getMeasurements(), productDto.getMeasurements());
        assertEquals(product.getMaterials(), productDto.getMaterials());
        assertEquals(product.getDescription(), productDto.getDescription());
        assertEquals(product.getPrice(), productDto.getPrice());
        assertEquals(product.getDeliveryOptions(), productDto.getDeliveryOptions());
        assertEquals(product.getUser().getUsername(), productDto.getUsername());
    }

    @Test
    @DisplayName("Should throw exception when measurements is null")
    void validateProductDto_MeasurementsIsNull_ShouldThrowIllegalArgumentException() {

        ProductDto productDto = new ProductDto();
        productDto.setProductTitle("The cool Couch");
        productDto.setCategory("Banken");
        productDto.setMeasurements(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> productService.validateProductDto(productDto));
        assertEquals("Measurements must not be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when measurements is empty")
    void validateProductDto_MeasurementsIsEmpty_ShouldThrowIllegalArgumentException() {

        ProductDto productDto = new ProductDto();
        productDto.setProductTitle("The cool Couch");
        productDto.setCategory("Banken");
        productDto.setMeasurements("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> productService.validateProductDto(productDto));
        assertEquals("Measurements must not be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should convert list of products to list of product DTOs")
    void productDtoList() {

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        List<ProductDto> productDtoList = productService.productDtoList(productList);

        assertEquals(productList.size(), productDtoList.size());
    }
}
