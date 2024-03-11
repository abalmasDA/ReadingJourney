package com.readingjourney.account.controller;

import com.readingjourney.account.dto.AuthResponse;
import com.readingjourney.account.dto.UserDto;
import com.readingjourney.account.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class responsible for handling registration-related requests. This controller provides
 * endpoints for user registration.
 */
@RestController
@RequestMapping("/auth/signup")
public class RegistrationController {

  private final RegistrationService registrationService;

  public RegistrationController(RegistrationService registrationService) {
    this.registrationService = registrationService;
  }

  /**
   * registerUser function to register a user.
   *
   * @param userDto the UserDto object containing user information
   * @return the authentication response after registering the user
   */
  @PostMapping
  public AuthResponse registerUser(@RequestBody @Valid UserDto userDto) {
    return registrationService.registerUser(userDto);
  }

}
