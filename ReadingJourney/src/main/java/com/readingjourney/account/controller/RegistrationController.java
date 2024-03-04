package com.readingjourney.account.controller;

import com.readingjourney.account.dto.AuthenticationResponse;
import com.readingjourney.account.dto.UserDto;
import com.readingjourney.account.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/signup")
public class RegistrationController {

  private final RegistrationService registrationService;

  public RegistrationController(RegistrationService registrationService) {
    this.registrationService = registrationService;
  }


  @PostMapping
  public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody @Valid UserDto userDto) {
    return ResponseEntity.ok(registrationService.registerUser(userDto));
  }
}
