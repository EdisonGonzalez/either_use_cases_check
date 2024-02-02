package com.either.service.validator;

import io.vavr.control.Either;

public class PasswordValidator {
  public static Either<String, String> validateLength(String password) {
    return password.length() >= 6
        ? Either.right(password)
        : Either.left("Password must be at least 6 characters long");
  }

  public static Either<String, String> validateCapitalLetter(String password) {
    return password.matches(".*[A-Z].*")
        ? Either.right(password)
        : Either.left("Password must contain at least one capital letter");
  }

  public static Either<String, String> validateDigit(String password) {
    return password.matches(".*\\d.*")
        ? Either.right(password)
        : Either.left("Password must contain at least one digit");
  }
}
