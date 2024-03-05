package com.readingjourney.service;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.readingjourney.account.dto.AuthResponse;
import com.readingjourney.account.dto.LoginDto;
import com.readingjourney.account.entity.User;
import com.readingjourney.account.jwt.JwtService;
import com.readingjourney.account.repository.UserRepository;
import com.readingjourney.account.service.LoginService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private JwtService jwtService;

  @Mock
  private AuthenticationManager authenticationManager;

  @InjectMocks
  private LoginService loginService;

  @Test
  public void loginUserTest() {

    LoginDto loginDto = new LoginDto("test@example.com", "test");
    User user = new User();
    user.setEmail(loginDto.getEmail());
    String expectedToken = "expectedToken";

    given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .willReturn(null);
    given(userRepository.findByEmail(loginDto.getEmail())).willReturn(Optional.of(user));
    given(jwtService.generateToken(user)).willReturn(expectedToken);
    AuthResponse result = loginService.loginUser(loginDto);

    assertThat(result).isNotNull();
    assertThat(result.getToken()).isEqualTo(expectedToken);
    verify(authenticationManager, times(1))
        .authenticate(
            new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
    verify(userRepository, times(1)).findByEmail(loginDto.getEmail());
    verify(jwtService, times(1)).generateToken(user);
  }

}
