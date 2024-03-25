package com.readingjourney.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.readingjourney.account.dto.UserDto;
import com.readingjourney.account.entity.User;
import com.readingjourney.account.exception.UserNotFoundException;
import com.readingjourney.account.mapper.UserMapper;
import com.readingjourney.account.repository.UserRepository;
import com.readingjourney.account.service.UserService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private UserMapper userMapper;
  @InjectMocks
  private UserService userService;
  private UserDto userDto;
  private User user;
  private long userId;

  @BeforeEach
  void setUp() {
    userId = 1L;
    userDto = new UserDto();
    user = new User();
    user.setId(1L);
  }

  @Test
  void findAllTest() {
    int expected = 1;
    when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
    List<User> users = userService.findAll();
    assertThat(users).hasSize(expected);
    verify(userRepository).findAll();
  }

  @Test
  void findByIdTest() {
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    Optional<User> user = userService.findById(userId);
    assertThat(user).isPresent();
    assertThat(user.get().getId()).isEqualTo(userId);
    verify(userRepository).findById(userId);
  }

  @Test
  void findByIdThrowExceptionTest() {
    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThatThrownBy(() -> userService.findById(userId))
        .isInstanceOf(UserNotFoundException.class);
    verify(userRepository).findById(userId);
  }

  @Test
  void saveTest() {
    when(userMapper.toEntity(any(UserDto.class))).thenReturn(user);
    when(userRepository.save(any(User.class))).thenReturn(user);
    User userToBeSaved = userService.save(userDto);
    assertThat(userToBeSaved).isNotNull();
    verify(userRepository).save(user);
    verify(userMapper).toEntity(userDto);
  }

  @Test
  void updateTest() {
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(userRepository.save(any(User.class))).thenReturn(user);
    User updatedUser = userService.update(userId, userDto);
    assertThat(updatedUser).isNotNull();
    verify(userRepository).findById(userId);
    verify(userRepository).save(user);
  }

  @Test
  void updateThrowExceptionTest() {
    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThatThrownBy(() -> userService.update(userId, userDto))
        .isInstanceOf(UserNotFoundException.class);
    verify(userRepository).findById(userId);
  }

  @Test
  void deleteTest() {
    when(userRepository.existsById(userId)).thenReturn(true);
    doNothing().when(userRepository).deleteById(userId);
    userService.delete(userId);
    verify(userRepository).deleteById(userId);
  }

  @Test
  void deleteThrowExceptionTest() {
    when(userRepository.existsById(anyLong())).thenReturn(false);
    assertThatThrownBy(() -> userService.delete(userId))
        .isInstanceOf(UserNotFoundException.class);
    verify(userRepository, never()).deleteById(userId);
  }

  @Test
  void deleteAllTest() {
    doNothing().when(userRepository).deleteAll();
    userService.deleteAll();
    verify(userRepository).deleteAll();
  }

}
