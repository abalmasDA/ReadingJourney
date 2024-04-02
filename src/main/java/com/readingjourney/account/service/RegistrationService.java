package com.readingjourney.account.service;

import com.readingjourney.account.dto.AuthResponse;
import com.readingjourney.account.dto.UserDto;
import com.readingjourney.account.entity.Role;
import com.readingjourney.account.entity.User;
import com.readingjourney.account.entity.UserDetailsImpl;
import com.readingjourney.account.jwt.JwtService;
import com.readingjourney.account.repository.UserRepository;
import com.readingjourney.notification.service.UserRegistrationProducerService;
import java.time.LocalDateTime;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The Registration Service. This class provides user registration functionality including user
 * creation and token generation.
 */
@Service
public class RegistrationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final UserRegistrationProducerService userRegistrationProducerService;

  /**
   * Instantiates a new Registration service.
   */
  public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
      JwtService jwtService, UserRegistrationProducerService userRegistrationProducerService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.userRegistrationProducerService = userRegistrationProducerService;
  }

  /**
   * Register a new user with the provided user information.
   *
   * @param userDto the user data transfer object containing user information
   * @return an authentication response containing a JWT token
   */
  public AuthResponse registerUser(UserDto userDto) {
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
    UserDetails userDetails = new UserDetailsImpl(user);
    var jwtToken = jwtService.generateToken(userDetails);
    userRegistrationProducerService.sendRegistrationMessage(user.getEmail());
    return AuthResponse.builder()
        .token(jwtToken)
        .build();
  }

}
