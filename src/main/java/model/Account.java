package model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import model.enums.Country;
import model.enums.Gender;

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
