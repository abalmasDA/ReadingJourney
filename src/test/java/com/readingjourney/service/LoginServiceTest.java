package com.readingjourney.service;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.readingjourney.account.dto.AuthResponse;
import com.readingjourney.account.dto.LoginDto;
import com.readingjourney.account.entity.User;
import com.readingjourney.account.exception.UserNotFoundException;
import com.readingjourney.account.jwt.JwtService;
import com.readingjourney.account.repository.UserRepository;
import com.readingjourney.account.service.LoginService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private JwtService jwtService;

  @Mock
  private AuthenticationManager authenticationManager;

  @InjectMocks
  private LoginService loginService;

  private LoginDto loginDto;

  private User user;

  private String expectedToken;


  @BeforeEach
  public void setUp() {
    loginDto = LoginDto
        .builder()
        .email("test@example.com")
        .password("test")
        .build();

    user = User
        .builder()
        .email(loginDto.getEmail())
        .build();

    expectedToken = "expectedToken";
  }

  @Test
  void loginUserTest() {

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(null);
    when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.of(user));
    when(jwtService.generateToken(any(UserDetails.class))).thenReturn(expectedToken);

    AuthResponse result = loginService.loginUser(loginDto);

    assertThat(result).isNotNull();
    assertThat(result.getToken()).isEqualTo(expectedToken);
    verify(authenticationManager)
        .authenticate(
            new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
    verify(userRepository).findByEmail(loginDto.getEmail());
    verify(jwtService).generateToken(any(UserDetails.class));
  }

  @Test
  void loginUserThrowsUserNotFoundExceptionTest() {

    when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.empty());

    assertThatThrownBy(() -> loginService.loginUser(loginDto))
        .isInstanceOf(UserNotFoundException.class)
        .hasMessageContaining("User with email %s not found", loginDto.getEmail());
    verify(authenticationManager).authenticate(
        new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
    verify(userRepository).findByEmail(loginDto.getEmail());
    verify(jwtService, never()).generateToken(any(UserDetails.class));
  }

}
