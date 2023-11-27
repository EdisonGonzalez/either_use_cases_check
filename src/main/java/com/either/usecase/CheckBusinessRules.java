package com.either.usecase;

import com.either.error.ErrorMessage;
import io.vavr.control.Either;
import org.springframework.stereotype.Component;

@Component
public class CheckBusinessRules {
    public Either<ErrorMessage, String> getEither(boolean isRight) {
        return isRight
                ? Either.left(ErrorMessage.builder().error("Field X is required").code("BC-002").build())
                : Either.right("Allowed for our business");
    }
}
