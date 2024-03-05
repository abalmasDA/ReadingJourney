package com.readingjourney.account.dto;

import com.readingjourney.account.constraint.PasswordStrength;
import com.readingjourney.account.constraint.PasswordStrengthCheck;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginDto {

  @NotBlank(message = "Email is required")
  @Email(message = "Invalid email format")
  @Size(max = 100, message = "Email must be less than or equal to 100 characters")
  private String email;

  @NotBlank(message = "Password is required")
  @PasswordStrengthCheck(PasswordStrength.STRONG)
  private String password;

}
