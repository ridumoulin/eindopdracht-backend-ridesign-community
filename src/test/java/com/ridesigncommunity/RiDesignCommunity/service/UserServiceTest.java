package com.ridesigncommunity.RiDesignCommunity.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ridesigncommunity.RiDesignCommunity.dto.UserInputDto;
import com.ridesigncommunity.RiDesignCommunity.dto.UserOutputDto;
import com.ridesigncommunity.RiDesignCommunity.model.User;
import com.ridesigncommunity.RiDesignCommunity.model.ImageData;
import com.ridesigncommunity.RiDesignCommunity.repository.UserRepository;
import com.ridesigncommunity.RiDesignCommunity.utils.ImageUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private ProductService productService;

    private User user;

    @BeforeEach
    public void setUp() throws IOException {
        user = new User("halinademol@outlook.com", "HalinaisCool", "Halina", "de Mol,", "HalinaDesignLover", false);


//        ImageData imgdata = new ImageData();
//        imgdata.setImageData(ImageUtil.compressImage(Files.readAllBytes(Paths.get("src/main/resources/images/products/" + imageFile))));
//        imgdata.setType("image/" + imageFile.substring(imageFile.lastIndexOf(".")));

        ImageData imageData = new ImageData();
        imageData.setImageData(ImageUtil.compressImage(Files.readAllBytes(Paths.get("src/test/java/resources/photo-profile-catrina.jpeg"))));
        imageData.setType("image/jpeg");
        user.setImageData(imageData);
    }

    @AfterEach
    void tearDown() {
        user = null;
    }

    @Test
    @DisplayName("Should get all users")
    void getAllUsers() {

        when(userRepositoryMock.findAll()).thenReturn(List.of(user));

        List<UserOutputDto> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("HalinaDesignLover", result.get(0).getUsername());
        verify(userRepositoryMock, times(1)).findAll();
    }

    @Test
    @DisplayName("Should register user")
    void registerUser() {
        UserInputDto userInputDto = new UserInputDto("halinademol@outlook.com", "HalinaisCool", "Halina", "de Mol", "HalinaDesignLover", false);

        when(userRepositoryMock.existsById(anyString())).thenReturn(false);
        when(passwordEncoderMock.encode(anyString())).thenReturn("$2a$10$3bIY.kIbJ7wxKlesCuevMe.tLDPPgGuK.fwhcWIrUhBg3JBcKGbQm");

        userService.registerUser(userInputDto);

        verify(userRepositoryMock, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should check if email exists")
    void existsByEmail() {

        when(userRepositoryMock.existsById(anyString())).thenReturn(true);


        boolean result = userService.existsByEmail("halinademol@outlook.com");

        assertTrue(result);
        verify(userRepositoryMock, times(1)).existsById(anyString());
    }

    @Test
    @DisplayName("Should authenticate user")
    void authenticateUser() {
        UserInputDto userInputDto = new UserInputDto("halinademol@outlook.com", "password");

        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoderMock.matches(anyString(), anyString())).thenReturn(true);

        boolean result = userService.authenticateUser(userInputDto);

        assertTrue(result);
        verify(userRepositoryMock, times(1)).findById(anyString());
        verify(passwordEncoderMock, times(1)).matches(anyString(), anyString());
    }


    @Test
    @DisplayName("Should get user by ID")
    void getUserById() {

        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));

        UserOutputDto result = userService.getUserById("halinademol@outlook.com");


        assertEquals("HalinaDesignLover", result.getUsername());
        verify(userRepositoryMock, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Should update username")
    void updateUsername() {

        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));

        boolean result = userService.updateUsername("halinademol@outlook.com", "HalinaLovesDesign");

        assertTrue(result);
        assertEquals("HalinaLovesDesign", user.getUsername());
        verify(userRepositoryMock, times(1)).findById(anyString());
        verify(userRepositoryMock, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should delete user")
    void deleteUser() {

        when(userRepositoryMock.findByUsername(anyString())).thenReturn(Optional.of(user));

        boolean result = userService.deleteUser("HalinaDesignLover");

        assertTrue(result);
        verify(userRepositoryMock, times(1)).delete(any(User.class));
    }

    @Test
    @DisplayName("Should get user by email")
    void getUserByEmail() {

        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));

        UserOutputDto result = userService.getUserByEmail("halinademol@outlook.com");

        assertNotNull(result);
        assertEquals("HalinaDesignLover", result.getUsername());
        verify(userRepositoryMock, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Should add favorite product")
    void addFavorite() {

        when(userRepositoryMock.findByUsername(anyString())).thenReturn(Optional.of(user));

        boolean result = userService.addFavorite("HalinaDesignLover", 1L);

        assertTrue(result);
        assertTrue(user.getFavorites().contains(1L));
        verify(userRepositoryMock, times(1)).findByUsername(anyString());
        verify(userRepositoryMock, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should remove favorite product")
    void removeFavorite() {

        user.getFavorites().add(1L);
        when(userRepositoryMock.findByUsername(anyString())).thenReturn(Optional.of(user));

        boolean result = userService.removeFavorite("HalinaDesignLover", 1L);

        assertTrue(result);
        assertFalse(user.getFavorites().contains(1L));
        verify(userRepositoryMock, times(1)).findByUsername(anyString());
        verify(userRepositoryMock, times(1)).save(any(User.class));
    }

}
