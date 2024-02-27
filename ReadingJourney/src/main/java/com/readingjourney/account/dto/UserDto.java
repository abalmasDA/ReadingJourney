package com.readingjourney.account.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

  @NotBlank(message = "First name is required")
  @Size(max = 100, message = "First name must be less than or equal to 100 characters")
  private String firstName;

  @NotBlank(message = "Last name is required")
  @Size(max = 100, message = "Last name must be less than or equal to 100 characters")
  private String lastName;

  @NotBlank(message = "Country is required")
  @Size(max = 100, message = "Country must be less than or equal to 100 characters")
  private String country;

  @NotBlank(message = "Email is required")
  @Email(message = "Invalid email format")
  @Size(max = 100, message = "Email must be less than or equal to 100 characters")
  private String email;

  @NotBlank(message = "Password is required")
  @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters long")
  private String password;

}
