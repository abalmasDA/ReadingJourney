package com.readingjourney.account.constraint;

import com.nulabinc.zxcvbn.Zxcvbn;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordStrengthValidator implements
    ConstraintValidator<PasswordStrengthCheck, String> {

  private PasswordStrength passwordStrength;

  @Override
  public void initialize(PasswordStrengthCheck constraintAnnotation) {
    this.passwordStrength = constraintAnnotation.value();
  }

  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    return new Zxcvbn().measure(password).getScore() >= passwordStrength.getStrengthNum();
  }
}
