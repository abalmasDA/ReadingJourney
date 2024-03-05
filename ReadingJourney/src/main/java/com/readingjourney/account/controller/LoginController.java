package com.readingjourney.account.controller;

import com.readingjourney.account.dto.AuthenticationResponse;
import com.readingjourney.account.dto.LoginDto;
import com.readingjourney.account.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/login")
public class LoginController {

  private final LoginService loginService;

  public LoginController(LoginService loginService) {
    this.loginService = loginService;
  }

  @PostMapping
  public ResponseEntity<AuthenticationResponse> LoginController(
      @RequestBody @Valid LoginDto loginDto) {
    return ResponseEntity.ok(loginService.loginUser(loginDto));
  }

}
