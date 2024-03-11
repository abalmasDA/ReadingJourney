package com.readingjourney.account.controller;

import com.readingjourney.account.dto.AuthResponse;
import com.readingjourney.account.dto.LoginDto;
import com.readingjourney.account.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling user authentication.
 */
@RestController
@RequestMapping("/auth/login")
public class LoginController {

  private final LoginService loginService;

  public LoginController(LoginService loginService) {
    this.loginService = loginService;
  }

  /**
   * loginController handles the POST request for user login.
   *
   * @param loginDto The LoginDto object containing user credentials
   * @return An AuthResponse object containing the user's authentication status
   */
  @PostMapping
  public AuthResponse loginController(@RequestBody @Valid LoginDto loginDto) {
    return loginService.loginUser(loginDto);
  }

}
