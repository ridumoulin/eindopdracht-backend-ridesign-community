package com.ridesigncommunity.RiDesignCommunity.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ridesigncommunity.RiDesignCommunity.dto.UserInputDto;
import com.ridesigncommunity.RiDesignCommunity.dto.UserOutputDto;
import com.ridesigncommunity.RiDesignCommunity.model.User;
import com.ridesigncommunity.RiDesignCommunity.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<Long> userIdCaptor;

    @Captor
    private ArgumentCaptor<String> emailCaptor;

    @BeforeEach
    public void setUp() {
        Mockito.reset(userRepositoryMock);
    }

    @Test
    public void testGetUserById_CatrinaUserExists() {
        User catrinaUser = new User();
        catrinaUser.setEmail("catrina.hollander@gmail.com");
        catrinaUser.setFirstname("Catrina");
        catrinaUser.setLastname("Hollander");
        catrinaUser.setUsername("CHDesigner");
        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(catrinaUser));

        Optional<UserOutputDto> userOutputDtoOptional = userService.getUserById(3L);

        assertTrue(userOutputDtoOptional.isPresent());
        UserOutputDto userOutputDto = userOutputDtoOptional.get();
        assertEquals(3L, userOutputDto.getUserId());
        assertEquals("catrina.hollander@gmail.com", userOutputDto.getEmail());
        assertEquals("Catrina", userOutputDto.getFirstname());
        assertEquals("Hollander", userOutputDto.getLastname());
        assertEquals("CHDesigner", userOutputDto.getUsername());

        verify(userRepositoryMock, times(1)).findById(userIdCaptor.capture());
        assertEquals(3L, userIdCaptor.getValue());
    }

    @Test
    public void testExistsByEmail_EmailExists() {
        String existingEmail = "catrina.hollander@gmail.com";
        when(userRepositoryMock.existsByEmail(existingEmail)).thenReturn(true);

        boolean result = userService.existsByEmail(existingEmail);

        assertTrue(result);

        verify(userRepositoryMock, times(1)).existsByEmail(emailCaptor.capture());
        assertEquals(existingEmail, emailCaptor.getValue());
    }

    @Test
    public void testExistsByEmail_EmailDoesNotExist() {
        String nonExistingEmail = "linadeboom@gmail.com";
        when(userRepositoryMock.existsByEmail(nonExistingEmail)).thenReturn(false);

        boolean result = userService.existsByEmail(nonExistingEmail);

        assertFalse(result);

        verify(userRepositoryMock, times(1)).existsByEmail(emailCaptor.capture());
        assertEquals(nonExistingEmail, emailCaptor.getValue());
    }

    @Test
    public void testRegisterUser_Success() {

        PasswordEncoder passwordEncoderMock = Mockito.mock(PasswordEncoder.class);

        String expectedEncodedPassword = "$2a$10$3bIY.kIbJ7wxKlesCuevMe.tLDPPgGuK.fwhcWIrUhBg3JBcKGbQm";
        Mockito.when(passwordEncoderMock.encode(Mockito.anyString())).thenReturn(expectedEncodedPassword);


        UserService userService = new UserService(userRepositoryMock, passwordEncoderMock);

        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setEmail("halinademol@outlook.com");
        userInputDto.setPassword("HalinaisCool");
        userInputDto.setFirstname("halina");
        userInputDto.setLastname("deMol");
        userInputDto.setUsername("HalinaDesignLover");
        userInputDto.setRiDesigner(false);

        when(userRepositoryMock.existsByEmail(userInputDto.getEmail())).thenReturn(false);

        userService.registerUser(userInputDto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepositoryMock, times(1)).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();

        assertNotNull(capturedUser);
        assertEquals(userInputDto.getEmail(), capturedUser.getEmail());
        assertEquals(expectedEncodedPassword, capturedUser.getPassword());
        assertEquals(userInputDto.getFirstname(), capturedUser.getFirstname());
        assertEquals(userInputDto.getLastname(), capturedUser.getLastname());
        assertEquals(userInputDto.getUsername(), capturedUser.getUsername());
        assertEquals(userInputDto.isRiDesigner(), capturedUser.isRiDesigner());
    }

    @Test
    public void testRegisterUser_EmailAlreadyExists() {

        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setEmail("olaf.holleman@outlook.com");
        userInputDto.setPassword("$2a$10$PWhlye6v88xBXenDW4W2U.qabxAWP1WbEte4aDXhQbFzJc3hV9fAC");
        userInputDto.setFirstname("Olaf");
        userInputDto.setLastname("Holleman");
        userInputDto.setUsername("brolaf");
        userInputDto.setRiDesigner(true);

        when(userRepositoryMock.existsByEmail(userInputDto.getEmail())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(userInputDto));

        verify(userRepositoryMock, never()).save(any());
    }

    @Test
    public void testAuthenticateUser_SuccessfulAuthentication() {

        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setEmail("riannedumoulin@gmail.com");
        userInputDto.setPassword("RiisAdmin");


        User existingUser = new User();
        existingUser.setEmail("riannedumoulin@gmail.com");
        existingUser.setPassword("$2a$10$0MNh5e7DP0vNV44QK10biu28kzxY3IEH47O6vX96rjChFstkg54S6");
        when(userRepositoryMock.findByEmail("riannedumoulin@gmail.com")).thenReturn(existingUser);


        when(passwordEncoderMock.matches(userInputDto.getPassword(), existingUser.getPassword())).thenReturn(true);

        boolean result = userService.authenticateUser(userInputDto);

        assertTrue(result);
    }

    @Test
    public void testAuthenticateUser_EmailNotFound() {

        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setEmail("linnachterhoek@gmail.com");
        userInputDto.setPassword("Linnisdebom");


        when(userRepositoryMock.findByEmail("linnachterhoek@gmail.com")).thenReturn(null);


        boolean result = userService.authenticateUser(userInputDto);

        assertFalse(result);
    }

    @Test
    public void testAddFavorite_Success() {
        Long username = 1L;
        Long productId = 4L;

        User user = new User();
        user.setUserUsername(username);
        user.setFavorites(new ArrayList<>());

        when(userRepositoryMock.findById(username)).thenReturn(Optional.of(user));

        boolean result = userService.addFavorite(username, productId);

        assertTrue(result);
        assertTrue(user.getFavorites().contains(productId));
        verify(userRepositoryMock, times(1)).save(user);
    }

    @Test
    public void testValidateUserInput_AllFieldsValid() {
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setUsername("HendriksDesigns");
        userInputDto.setPassword("HendrikDesignsareamazing");
        userInputDto.setEmail("Hendrik.postemea@outlook.com");
        userInputDto.setFirstname("Hendrik");
        userInputDto.setLastname("Postema");

        assertDoesNotThrow(() -> userService.validateUserInput(userInputDto));
    }

    @Test
    public void testValidateUserInput_EmptyField() {
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setUsername("FinnDesign");
        userInputDto.setPassword("");
        userInputDto.setEmail("finn1995@gmail.com");
        userInputDto.setFirstname("Finn");
        userInputDto.setLastname("deJong");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.validateUserInput(userInputDto));
        assertEquals("Password cannot be empty.", exception.getMessage());
    }
}
