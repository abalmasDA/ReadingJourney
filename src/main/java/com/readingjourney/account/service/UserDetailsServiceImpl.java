package com.readingjourney.account.service;

import com.readingjourney.account.entity.User;
import com.readingjourney.account.entity.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * The UserDetailsServiceImpl. This class implements the UserDetailsService interface for loading
 * user details by email.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserService userService;

  public UserDetailsServiceImpl(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userService.findUserByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException(email);
    }
    return new UserDetailsImpl(user);
  }

}
