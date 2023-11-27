package com.either.usecase;

import com.either.error.ErrorMessage;
import com.either.exception.ResourceNotFoundException;
import io.vavr.control.Either;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CheckBusinessRules {
    public Either<ErrorMessage, String> getEither(boolean isRight) {
        return isRight
                ? Either.left(ErrorMessage.builder().error("Field X is required").code("BC-002").build())
                : Either.right("Allowed for our business");
    }

    public void getError() {
        UUID uuid = UUID.randomUUID();
        throw new ResourceNotFoundException("with ID: " + uuid);
    }
}
