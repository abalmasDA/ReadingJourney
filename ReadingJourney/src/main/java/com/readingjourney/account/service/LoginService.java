package com.readingjourney.account.service;

import com.readingjourney.account.dto.AuthResponse;
import com.readingjourney.account.dto.LoginDto;
import com.readingjourney.account.exception.UserNotFoundException;
import com.readingjourney.account.jwt.JwtService;
import com.readingjourney.account.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public LoginService(UserRepository userRepository, JwtService jwtService,
      AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }

  /**
   * Logs in a user using the provided login information.
   *
   * @param loginDto the login information including email and password
   * @return the authentication response containing a JWT token
   */
  public AuthResponse loginUser(LoginDto loginDto) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginDto.getEmail(),
            loginDto.getPassword()
        )
    );
    var user = userRepository
        .findByEmail(loginDto.getEmail())
        .orElseThrow(() -> new UserNotFoundException("User not found"));
    var jwtToken = jwtService.generateToken(user);
    return AuthResponse
        .builder()
        .token(jwtToken)
        .build();
  }

}

