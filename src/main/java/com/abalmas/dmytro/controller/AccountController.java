package com.abalmas.dmytro.controller;

import com.abalmas.dmytro.model.Account;
import com.abalmas.dmytro.service.AccountService;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountController {

  private final AccountService accountService;


  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }


  @GetMapping("/accounts")
  public List<Account> findAll() {
    return accountService.findAll();
  }

  @GetMapping("/accounts/{id}")
  public Optional <Account> findById(@PathVariable("id") long id) {
    return accountService.findById(id);
  }

  @PostMapping("/accounts")
  public Account add(@RequestBody Account account) {
    return accountService.add(account);
  }

  @PutMapping("/accounts/{id}")
  public Account update(@PathVariable("id") long id, @RequestBody Account account) {
    return accountService.update(id, account);
  }

  @DeleteMapping("/accounts/{id}")
  public void delete(@PathVariable("id") long id) {
    accountService.delete(id);
  }

  @GetMapping
  public String getHello() {
    return "hello";
  }

  @PostMapping("/accounts/{balance}")
  public List<Account> findExceedingBalance(@PathVariable("balance") double balance,
      @RequestBody List<Account> account) {
    return accountService.findExceedingBalance(account, balance);
  }

}
