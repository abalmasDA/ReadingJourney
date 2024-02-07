package com.abalmas.dmytro.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.abalmas.dmytro.model.enums.Country;
import com.abalmas.dmytro.model.enums.Gender;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

  private String firstName;
  private String lastName;
  private Country country;
  private LocalDate birthday;
  private double balance;
  private Gender gender;

}
