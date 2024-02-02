package com.either.service;

import com.either.service.validator.PasswordValidator;
//import io.vavr.Function1;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {
  //  public Either<String, String> validatePassword(String password) {
  //    Function1<String, Either<String, String>> validateChain =
  //        (pass) ->
  //            PasswordValidator.validateLength(pass)
  //                .flatMap(PasswordValidator::validateCapitalLetter)
  //                .flatMap(PasswordValidator::validateDigit);
  //
  //    return validateChain.apply(password);
  //  }

  public Either<List<String>, String> validatePassword2(String password) {
    List<Either<String, String>> validationResults =
        List.of(
            PasswordValidator.validateLength(password),
            PasswordValidator.validateCapitalLetter(password),
            PasswordValidator.validateDigit(password));

    List<String> errors =
        validationResults.stream().filter(Either::isLeft).map(Either::getLeft).toList();

    return errors.isEmpty()
        ? Either.right(password) // No errors, return valid password
        : Either.left(errors); // Return list of validation errors
  }
}
