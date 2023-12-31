package com.either.controller;

import com.either.common.error.ErrorMessage;
import com.either.mapper.AccountMapper;
import com.either.model.dto.AccountResponse;
import com.either.model.dto.BodyResponse;
import com.either.usecase.CheckBusinessRules;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ControllerExample {
  private final CheckBusinessRules checkBusinessRules;
  private final AccountMapper accountMapper;

  @GetMapping(value = "/validate", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> validate() {
    return checkBusinessRules
        .getEither(new Random().nextBoolean())
        .fold(
            errorMessage -> new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST),
            result ->
                new ResponseEntity<>(
                    BodyResponse.builder().message(result).build(), HttpStatus.OK));
  }

  @GetMapping(value = "/error")
  public void error() {
    checkBusinessRules.getError();
  }

  @GetMapping(
      value = "/get/all/accounts/{resourceName}/operation",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getAllAccounts(@RequestParam String filterParams) {
    log.info("getAllAccounts with operation {}", filterParams);
    Either<ErrorMessage, List<AccountResponse>> result =
        checkBusinessRules
            .applyOperationsWithEither(filterParams)
            .map(accountMapper::mapDomainToDtoList);
    log.debug("result: {}", result);
    return result.fold(
        errorMessage -> new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST),
        accountResponseList -> new ResponseEntity<>(accountResponseList, HttpStatus.OK));
  }
}
