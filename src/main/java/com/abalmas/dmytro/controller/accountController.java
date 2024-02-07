package com.abalmas.dmytro.controller;

import com.abalmas.dmytro.model.Account;
import com.abalmas.dmytro.service.AccountService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class accountController {

  private final AccountService accountService;

  @Autowired
  public accountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping
  public String getHello() {
    return "hello";
  }

  @PostMapping("/accounts/{balance}")
  public List<Account> findExceedingBalance(@PathVariable("balance") double balance,
      @RequestBody List<Account> accounts) {
    return accountService.findExceedingBalance(accounts, balance);
  }
}
