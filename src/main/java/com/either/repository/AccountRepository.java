package com.either.repository;

import com.either.common.domain.Account;
import com.either.common.error.ErrorMessage;
import io.vavr.control.Either;

import java.util.List;

public interface AccountRepository {
    Either<ErrorMessage, List<Account>> getAll(String resourceName);
}
