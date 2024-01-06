package com.either.usecase;

import com.either.common.domain.Account;
import com.either.common.error.ErrorMessage;
import com.either.common.exception.ResourceNotFoundException;
import com.either.service.AccountService;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class CheckBusinessRules {
  private final AccountService accountService;

  public Either<ErrorMessage, String> getEither(boolean isRight) {
    return isRight
        ? Either.left(ErrorMessage.builder().error("Field X is required").code("BC-002").build())
        : Either.right("Allowed for our business");
  }

  public void getError() {
    UUID uuid = UUID.randomUUID();
    throw new ResourceNotFoundException("with ID: " + uuid);
  }

  public Either<ErrorMessage, List<Account>> applyOperationsWithEither(String filterParams) {
    Either<ErrorMessage, List<Account>> dbAccountList =
        accountService
            .getAllApplyingFilter(filterParams);
            //.filter(accountList -> accountList.stream().filter(Account::isActive).toList()); // Note: Comment code is part of exposition
    log.debug("dbAccountList: {}", dbAccountList);
    return dbAccountList;
  }
}
