package com.either.service;

import com.either.service.validator.PasswordValidator;
import io.vavr.Function1;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {
    public Either<String, String> validatePassword(String password) {
    Function1<String, Either<String, String>> validateChain =
        (pass) ->
            PasswordValidator.validateLength(pass)
                .flatMap(PasswordValidator::validateCapitalLetter)
                .flatMap(PasswordValidator::validateDigit);

        return validateChain.apply(password);
    }
}
