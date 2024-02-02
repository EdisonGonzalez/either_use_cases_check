package com.either.controller;

import com.either.common.domain.Account;
import com.either.common.domain.NewError;
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
import org.springframework.web.bind.annotation.*;

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

  @GetMapping(value = "/get/all/accounts/operation", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getAllAccounts(@RequestParam(required = false) String filterParams) {
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

  @GetMapping(value = "/check/operations", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> checkOperations() {
    log.info("checkOperations");
    checkBusinessRules.moreOperationsWithEither();
    log.trace("checkOperations finished");
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(value = "/get/account/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getAccountById(@PathVariable("id") String id) {
    log.info("getAccountById: {}", id);
    Either<NewError, Account> result = checkBusinessRules.getAccountById(id);
    log.trace("getAccountById finished with result: {}", result);
    return result.fold(
        errorMessage -> new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST),
        account -> new ResponseEntity<>(account, HttpStatus.OK));
  }

  @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> registerUser(
      @RequestParam String username, @RequestParam String password) {
    log.trace("register user: {}", username);
    //    Either<String, String> result =
    //            checkBusinessRules.checkPassword(password);
    Either<List<String>, String> result = checkBusinessRules.checkPassword(password);
    log.debug("result: {}", result);
    return result.fold(
        errorMessage -> new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST),
        okValidation -> new ResponseEntity<>(okValidation, HttpStatus.OK));
  }
}
