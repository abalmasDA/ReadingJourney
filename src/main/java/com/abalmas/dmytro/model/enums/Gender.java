package main.java.com.abalmas.dmytro.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {

  MALE("Male"),
  FEMALE("Female");

  private final String value;

}
