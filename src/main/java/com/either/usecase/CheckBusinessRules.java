package com.either.usecase;

import com.either.common.domain.Account;
import com.either.common.error.ErrorMessage;
import com.either.common.exception.ResourceNotFoundException;
import com.either.service.AccountService;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
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
        accountService.getAllApplyingFilter(filterParams);
    // .filter(accountList -> accountList.stream().filter(Account::isActive).toList()); // Note:
    // Comment code is part of exposition
    log.debug("dbAccountList: {}", dbAccountList);
    return dbAccountList;
  }

  public void moreOperationsWithEither() {
    final Either<String, Integer> EITHER_LEFT_DEFAULT = Either.left("Without details");
    final Either<String, Integer> EITHER_RIGHT_DEFAULT = Either.right(0);
    Either<String, Integer> either1 = Either.left(null); // returns Either.left(null)
    Either<String, Integer> either2 = Either.right(42); // returns Either.right(42)
    log.info(
        "Two Either objects were created. \n either1: {} (Check creating an Either instance from a nullable value) \n either2: {}",
        either1,
        either2);

    boolean isLeft = either1.isLeft(); // true
    boolean isRight = either1.isRight(); // false
    log.info(
        "Checking whether an Either instance is a Left or a Right: \n either1.isLeft() {} \n either1.isRight(): {}",
        isLeft,
        isRight);

    String leftValue = either1.getLeft(); // null
    Integer rightValue = either2.get(); // 42
    log.info(
        "Getting the value of an Either instance. \n either1.getLeft(): {} (If you want to get the left part, you should indicate that is left) \n either2.get(): {}",
        leftValue,
        rightValue);

    Either<String, String> either3 = either1.map(Object::toString); // returns Either.left(null)
    Either<String, String> either4 = either2.map(Object::toString); // returns Either.right("42")
    // Either<String, Integer> either5 =
    // either1
    // .mapLeft(Object::toString)
    // .orElse(EITHER_LEFT_DEFAULT); // Cause nullPointerException, part of exposition
    Either<String, Integer> either6 = either2.mapLeft(Object::toString); // returns Either.right(42)
    log.info(
        "Mapping the value of an Either instance if it is a Right. \n either1.map(): {} (It is left so continue the same value) \n either2.map(): {}. \n either2.mapLeft(): {} (It is left so continue the same value)",
        either3,
        either4,
        either6);

    Either<String, Integer> either7 =
        either1.flatMap(i -> Either.right(i + 1)); // returns Either.left(null)
    Either<String, Integer> either8 =
        either2.flatMap(i -> Either.right(i + 1)); // returns Either.right(43)
    log.info(
        "Flat-mapping the value of an Either instance. \n either1.flatMap: {} (Check not nullPointerException) \n either2.flatMap: {}",
        either7,
        either8);

    Option<Either<String, Integer>> either9 =
        either1.filter(
            i -> i > 0); // returns Either.left("Left value null does not match predicate.")
    Either<String, Integer> either10 = either9.get().orElse(EITHER_LEFT_DEFAULT);
    Option<Either<String, Integer>> either11 =
        either2.filter(i -> i > 0); // returns Either.right(42)
    Either<String, Integer> either12 = either11.get().orElse(EITHER_RIGHT_DEFAULT);
    log.info(
        "Filtering the value of an Either instance. \n either1.filter: {} \n either9.getOrElse: {} \n either2.filter: {} \n either10.getOrElse: {}",
        either9,
        either10,
        either11,
        either12);

    Either<Integer, String> either13 = either1.swap(); // returns Either.right(null)
    Either<Integer, String> either14 = either2.swap(); // returns Either.left(42)
    log.info(
        "Swapping the sides of an Either instance. \n either1.swap: {} \n either2.swap: {}",
        either13,
        either14);

    Optional<Integer> optional1 = either1.toJavaOptional(); // returns Optional.empty()
    List<Integer> list1 = either2.toJavaList(); // returns List.of(42)
    log.info(
        "Converting an Either instance to an Optional or Collection. \n either1.toJavaOptional: {} \n either2.toJavaList: {}",
        optional1,
        list1);

    String result1 = either1.fold(l -> "Left: " + l, r -> "Right: " + r); // returns "Left: null"
    String result2 = either2.fold(l -> "Left: " + l, r -> "Right: " + r); // returns "Right: 42"
    log.info(
        "Folding the value of an Either instance with a function for each side. \n either1.fold: {} \n either2.fold: {}",
        result1,
        result2);

    Try<Integer> try1 = Try.of(() -> 1 / 0); // returns Either.left(ArithmeticException)
    Try<Integer> try2 = Try.of(() -> 1 / 1); // returns Either.right(1)
    log.info(
        "---- * ------- \n Creating an Try instance from a computation that may throw an exception. \n 1/0: {} \n 1/1: {}",
        try1,
        try2);
  }
}
