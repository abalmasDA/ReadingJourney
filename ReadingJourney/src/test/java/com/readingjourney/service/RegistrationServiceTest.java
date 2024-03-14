package com.readingjourney.service;


import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.readingjourney.account.dto.AuthResponse;
import com.readingjourney.account.dto.UserDto;
import com.readingjourney.account.entity.Role;
import com.readingjourney.account.entity.User;
import com.readingjourney.account.jwt.JwtService;
import com.readingjourney.account.repository.UserRepository;
import com.readingjourney.account.service.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Mock
  private JwtService jwtService;

  @InjectMocks
  private RegistrationService registrationService;
  private UserDto userDto;
  private User savedUser;
  private String encodedPassword;
  private String expectedToken;


  @BeforeEach
  public void setUp() {
    userDto = UserDto
        .builder()
        .firstName("Test")
        .lastName("Test")
        .country("Test")
        .email("test@gmail")
        .password("inputPassword")
        .build();

    encodedPassword = "encodedPassword";
    expectedToken = "expectedToken";
  }

  @Test
  public void registerUserTest() {

    when(passwordEncoder.encode(userDto.getPassword())).thenReturn(encodedPassword);
    when(jwtService.generateToken(any(UserDetails.class))).thenReturn(expectedToken);

    AuthResponse result = registrationService.registerUser(userDto);

    assertThat(result).isNotNull();
    assertThat(result.getToken()).isEqualTo(expectedToken);

    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
    verify(userRepository).save(userCaptor.capture());
    verify(passwordEncoder).encode(userDto.getPassword());
    verify(jwtService).generateToken(any(UserDetails.class));

    savedUser = userCaptor.getValue();
    assertThat(savedUser.getEmail()).isEqualTo(userDto.getEmail());
    assertThat(savedUser.getFirstName()).isEqualTo(userDto.getFirstName());
    assertThat(savedUser.getLastName()).isEqualTo(userDto.getLastName());
    assertThat(savedUser.getCountry()).isEqualTo(userDto.getCountry());
    assertThat(savedUser.getPassword()).isEqualTo(encodedPassword);
    assertThat(savedUser.getRole()).isEqualTo(Role.USER);
    assertThat(savedUser.getCreatedAt()).isNotNull();
  }

}
