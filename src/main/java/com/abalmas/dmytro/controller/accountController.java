package com.abalmas.dmytro.controller;

import com.abalmas.dmytro.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class accountController {

  private final AccountService accountService;


  @GetMapping
  public String getHello() {
    return "hello";
  }

}
