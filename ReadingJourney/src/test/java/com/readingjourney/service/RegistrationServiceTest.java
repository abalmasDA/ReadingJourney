package com.readingjourney.service;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.assertj.core.api.Assertions.assertThat;

import com.readingjourney.account.dto.AuthResponse;
import com.readingjourney.account.dto.UserDto;
import com.readingjourney.account.entity.User;
import com.readingjourney.account.jwt.JwtService;
import com.readingjourney.account.repository.UserRepository;
import com.readingjourney.account.service.RegistrationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

  @Test
  public void registerUserTest() {

    UserDto userDto = new UserDto("Test", "Test", "Test",
        "test@gmail.com", "inputPassword");
    String encodedPassword = "encodedPassword";
    String expectedToken = "JwtToken";

    given(passwordEncoder.encode(userDto.getPassword())).willReturn(encodedPassword);
    given(jwtService.generateToken(any(User.class))).willReturn(expectedToken);
    AuthResponse result = registrationService.registerUser(userDto);

    assertThat(result).isNotNull();
    assertThat(result.getToken()).isEqualTo(expectedToken);
    then(userRepository).should(times(1)).save(any(User.class));
    then(passwordEncoder).should(times(1)).encode(userDto.getPassword());
    then(jwtService).should(times(1)).generateToken(any(User.class));
  }

}
