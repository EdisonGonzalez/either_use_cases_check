package com.either.controller;

import com.either.response.ResponseBody;
import com.either.usecase.CheckBusinessRules;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequiredArgsConstructor
public class ControllerExample {
    private final CheckBusinessRules checkBusinessRules;

    @GetMapping(value = "/validate",    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validate() {
        return checkBusinessRules.getEither(new Random().nextBoolean())
                .fold(
                        errorMessage -> new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST),
                        result -> new ResponseEntity<>(ResponseBody.builder().message(result).build(), HttpStatus.OK));
    }
}
