package com.readingjourney.account.constraint;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {PasswordStrengthValidator.class})
public @interface PasswordStrengthCheck {

  String message() default
      "The chosen password is too weak. "
          + "Try mixing uppercase letters, lowercase letters, numbers and special characters.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  PasswordStrength value();
}
