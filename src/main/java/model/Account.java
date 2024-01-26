package model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Account {

  private String firstName;
  private String lastName;
  private String country;
  private LocalDate birthday;
  private double balance;
  private Gender gender;

}
