package com.readingjourney.account.constraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum PasswordStrength {

  WEAK("Weak", 0),
  FAIR("Fair", 1),
  GOOD("Good", 2),
  STRONG("Strong", 3),
  VERY_STRONG("Very strong", 4);

  private final String strength;
  private final int strengthNum;

}
