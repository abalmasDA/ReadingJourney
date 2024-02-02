package com.abalmas.dmytro.model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import com.abalmas.dmytro.model.enums.Country;
import com.abalmas.dmytro.model.enums.Gender;

@Data
@Builder
public class Account {

  private String firstName;
  private String lastName;
  private Country country;
  private LocalDate birthday;
  private double balance;
  private Gender gender;

}
