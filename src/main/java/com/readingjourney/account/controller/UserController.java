package com.readingjourney.account.controller;

import com.readingjourney.account.dto.UserDto;
import com.readingjourney.account.entity.User;
import com.readingjourney.account.service.UserService;
import jakarta.validation.Valid;
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
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<User> findAll() {
    return userService.findAll();
  }

  @GetMapping("/{id}")
  public Optional<User> findById(@PathVariable("id") long id) {
    return userService.findById(id);
  }

  @PostMapping
  public User add(@Valid @RequestBody UserDto userDto) {
    return userService.save(userDto);
  }

  @PutMapping("/{id}")
  public User update(@PathVariable("id") long id, @Valid @RequestBody UserDto userDto) {
    return userService.update(id, userDto);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") long id) {
    userService.delete(id);
  }

}
