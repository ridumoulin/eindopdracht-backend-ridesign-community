//package com.ridesigncommunity.RiDesignCommunity.service;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.junit.jupiter.api.extension.ExtendWith;
//import java.util.Collections;
//import com.ridesigncommunity.RiDesignCommunity.dto.ProductDto;
//import com.ridesigncommunity.RiDesignCommunity.model.Product;
//import com.ridesigncommunity.RiDesignCommunity.repository.ProductRepository;
//import com.ridesigncommunity.RiDesignCommunity.repository.UserRepository;
//import com.ridesigncommunity.RiDesignCommunity.service.ProductService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import java.util.Optional;
//
//@ExtendWith(MockitoExtension.class)
//public class ProductServiceTest {
//
//    @Mock
//    private ProductRepository productRepositoryMock;
//
//    @Mock
//    private UserRepository userRepositoryMock;
//
//    @InjectMocks
//    private ProductService productService;
//
//    @BeforeEach
//    public void setUp() {
//        Mockito.reset(productRepositoryMock, userRepositoryMock);
//    }
//
//    @Test
//    public void testCreateProduct_Success() {
//        // Arrange
//        ProductDto productDto = new ProductDto();
//        productDto.setProductTitle("Lounge Table Set cozy");
//        productDto.setCategory("Tafels");
//        productDto.setMeasurements("100x50x60 cm");
//        productDto.setMaterials("Hout, staal");
//        productDto.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vestibulum sagittis nisi, eu consectetur velit. Cras nec nunc eget arcu dictum vulputate. Proin aliquet quam quis magna consequat, ac blandit eros finibus. Nam auctor vestibulum velit, nec mattis enim venenatis et. Duis eget feugiat elit.");
//        productDto.setPrice(139.99);
//        productDto.setDeliveryOptions(Collections.singletonList("Bezorgen"));
//        productDto.setUsername("riannedumoulin@gmail.com");
//
//        Product expectedProduct = new Product();
//        expectedProduct.setProductId(7L); // Set whatever appropriate
//        expectedProduct.setProductTitle(productDto.getProductTitle());
//        expectedProduct.setCategory(productDto.getCategory());
//        expectedProduct.setMeasurements(productDto.getMeasurements());
//        expectedProduct.setMaterials(productDto.getMaterials());
//        expectedProduct.setDescription(productDto.getDescription());
//        expectedProduct.setPrice(productDto.getPrice());
//        expectedProduct.setDeliveryOptions(productDto.getDeliveryOptions());
//
//        when(userRepositoryMock.findByUsername(productDto.getUsername())).thenReturn(Optional.ofNullable(null));
//        when(productRepositoryMock.save(any(Product.class))).thenReturn(expectedProduct);
//
//        /// Act
//        ProductDto createdProductDto = productService.createProduct(productDto);
//
//        // Assert
//        assertNotNull(createdProductDto);
//        assertEquals(expectedProduct.getProductTitle(), createdProductDto.getProductTitle());
//        assertEquals(expectedProduct.getCategory(), createdProductDto.getCategory());
//        assertEquals(expectedProduct.getMeasurements(), createdProductDto.getMeasurements());
//        assertEquals(expectedProduct.getMaterials(), createdProductDto.getMaterials());
//        assertEquals(expectedProduct.getDescription(), createdProductDto.getDescription());
//        assertEquals(expectedProduct.getPrice(), createdProductDto.getPrice());
//        assertEquals(expectedProduct.getDeliveryOptions(), createdProductDto.getDeliveryOptions());
//
//        // Verify
//        verify(productRepositoryMock, times(1)).save(any(Product.class));
//        verify(userRepositoryMock, times(1)).findByUsername(productDto.getUsername());
//    }
//}
