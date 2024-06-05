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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import com.ridesigncommunity.RiDesignCommunity.model.Product;
import com.ridesigncommunity.RiDesignCommunity.repository.ProductRepository;
import com.ridesigncommunity.RiDesignCommunity.repository.UserRepository;

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
    void init() {
        user = new User("HillieDesign");

        ImageData savedImageData = new ImageData();
        savedImageData.setName("saved_image.jpg");
        savedImageData.setType("image/jpeg");
        savedImageData.setImageData(new byte[]{1, 2, 3});

        byte[] compressedImageData = new byte[]{4, 5, 6};

        ImageData imageData1 = new ImageData();
        imageData1.setName("image1.jpg");
        imageData1.setType("image/jpeg");
        imageData1.setImageData(new byte[]{1, 2, 3});

        ImageData imageData2 = new ImageData();
        imageData2.setName("image2.jpg");
        imageData2.setType("image/jpeg");
        imageData2.setImageData(new byte[]{4, 5, 6});

        ImageData imageData3 = new ImageData();
        imageData3.setName("image3.jpg");
        imageData3.setType("image/jpeg");
        imageData3.setImageData(new byte[]{7, 8, 9});

        List<ImageData> images = List.of(imageData1, imageData2, imageData3);

        product = new Product(1L, "Bank in nieuw jasje", images, "Banken", "2000cm x 80cm x 100cm", "Hout en textiel", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.", 399.99, List.of("Bezorgen", "Ophalen"), user);

        imageData1.setProduct(product);
        imageData2.setProduct(product);
        imageData3.setProduct(product);
        product.setUser(user);

        when(imageDataRepositoryMock.save(any(ImageData.class))).thenReturn(savedImageData);
        when(imageUtilMock.compressImage(any(byte[].class))).thenReturn(compressedImageData);
    }

    @AfterEach
    void tearDown() {
        product = null;
        user = null;
    }

    @Test
    @DisplayName("Should successfully create product")
    void createProduct() {
        // Arrange
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

        // Act
        ProductDto createdProduct = productService.createProduct(productDto);

        // Assert
        assertNotNull(createdProduct);
        assertEquals(product.getProductId(), createdProduct.getProductId());
        assertEquals(product.getProductTitle(), createdProduct.getProductTitle());
        verify(productRepositoryMock, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Should get all products")
    void getAllProducts() {
        // Arrange
        when(productRepositoryMock.findAll()).thenReturn(List.of(product));

        // Act
        List<ProductDto> result = productService.getAllProducts();

        // Assert
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
        // Arrange
        when(productRepositoryMock.getReferenceById(anyLong())).thenReturn(product);

        // Act
        productService.deleteProduct(1L);

        // Assert
        verify(productRepositoryMock, times(1)).deleteById(1L);
    }


    @Test
    @DisplayName("Should get products by category")
    void getProductsByCategory() {
        // Arrange
        String category = "Banken";
        when(productRepositoryMock.findByCategoryIgnoreCase(category)).thenReturn(List.of(product));

        // Act
        List<ProductDto> result = productService.getProductsByCategory(category);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Bank in nieuw jasje", result.get(0).getProductTitle());
    }

    @Test
    @DisplayName("Should get products by username")
    void getProductsByUsername() {
        // Arrange
        String username = "HillieDesign";
        when(userRepositoryMock.existsById(username)).thenReturn(true);
        when(productRepositoryMock.findProductByUser_Username(username)).thenReturn(List.of(product));

        // Act
        List<Product> result = productService.getProductsByUsername(username);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Bank in nieuw jasje", result.get(0).getProductTitle());
    }

    @Test
    @DisplayName("Should get product by Id")
    void getProductById() {
        // Arrange
        Long productId = 1L;
        when(productRepositoryMock.getById(productId)).thenReturn(product);

        // Act
        ProductDto result = productService.getProductById(productId);

        // Assert
        assertNotNull(result);
        assertEquals("Bank in nieuw jasje", result.getProductTitle());
    }

    @Test
    @DisplayName("Should search products by category and product title")
    void searchProducts() {
        // Arrange
        String category = "Banken";
        String productTitle = "Bank";
        when(productRepositoryMock.findByCategoryAndProductTitleContainingIgnoreCase(category, productTitle)).thenReturn(List.of(product));

        // Act
        List<ProductDto> result = productService.searchProducts(category, productTitle);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Bank in nieuw jasje", result.get(0).getProductTitle());
    }

    @Test
    @DisplayName("Should search products by category only")
    void searchProductsByCategory() {
        // Arrange
        String category = "Banken";
        when(productRepositoryMock.findByCategoryIgnoreCase(category)).thenReturn(List.of(product));

        // Act
        List<ProductDto> result = productService.searchProducts(category, null);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Bank in nieuw jasje", result.get(0).getProductTitle());
    }

    @Test
    @DisplayName("Should throw exception when product title is null")
    void createProductWithNullTitle() {
        // Arrange
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
        // Arrange
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
        // Arrange
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
        // Act
        ProductDto productDto = productService.fromModelToProductDto(product);

        // Assert
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

}
