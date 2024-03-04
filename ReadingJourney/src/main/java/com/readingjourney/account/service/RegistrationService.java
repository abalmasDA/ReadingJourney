package com.readingjourney.account.service;

import com.readingjourney.account.dto.AuthenticationResponse;
import com.readingjourney.account.dto.UserDto;
import com.readingjourney.account.entity.Role;
import com.readingjourney.account.entity.User;
import com.readingjourney.account.jwt.JwtService;
import com.readingjourney.account.repository.UserRepository;
import java.time.LocalDateTime;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public RegistrationService(UserRepository userRepository,
      PasswordEncoder passwordEncoder, JwtService jwtService) {
    this.jwtService = jwtService;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public AuthenticationResponse registerUser(UserDto userDto) {
    User user = User.builder()
        .firstName(userDto.getFirstName())
        .lastName(userDto.getLastName())
        .country(userDto.getCountry())
        .email(userDto.getEmail())
        .password(passwordEncoder.encode(userDto.getPassword()))
        .createdAt(LocalDateTime.now())
        .role(Role.USER)
        .build();
    userRepository.save(user);
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

}
