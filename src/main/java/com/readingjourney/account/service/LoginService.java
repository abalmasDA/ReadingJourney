package com.readingjourney.account.service;

import com.readingjourney.account.dto.AuthResponse;
import com.readingjourney.account.dto.LoginDto;
import com.readingjourney.account.entity.User;
import com.readingjourney.account.entity.UserDetailsImpl;
import com.readingjourney.account.exception.UserNotFoundException;
import com.readingjourney.account.jwt.JwtService;
import com.readingjourney.account.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * The Login Service. This class provides user login functionality including authentication and
 * token generation.
 */
@Service
public class LoginService {

  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  /**
   * Instantiates a new Login service.
   *
   * @param userRepository        the user repository
   * @param jwtService            the jwt service
   * @param authenticationManager the authentication manager
   */
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
    User user = userRepository
        .findByEmail(loginDto.getEmail())
        .orElseThrow(() -> new UserNotFoundException(loginDto.getEmail()));
    UserDetails userDetails = new UserDetailsImpl(user);
    var jwtToken = jwtService.generateToken(userDetails);
    return AuthResponse
        .builder()
        .token(jwtToken)
        .build();
  }

}

