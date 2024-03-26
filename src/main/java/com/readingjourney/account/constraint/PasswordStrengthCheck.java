package com.readingjourney.account.constraint;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The interface Password strength check.
 */
@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {PasswordStrengthValidator.class})
public @interface PasswordStrengthCheck {

  /**
   * Description of the message method.
   *
   * @return description of the return value
   */
  String message() default
      "The chosen password is too weak. "
          + "Try mixing uppercase letters, lowercase letters, numbers and special characters.";

  /**
   * Description of the groups method.
   *
   * @return description of the return value
   */
  Class<?>[] groups() default {};

  /**
   * Description of the payload method.
   *
   * @return description of the return value
   */
  Class<? extends Payload>[] payload() default {};

  /**
   * Description of the value method.
   *
   * @return description of the return value
   */
  PasswordStrength value();
}
