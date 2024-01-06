package com.either.service;

import com.either.common.domain.Account;
import com.either.common.error.ErrorMessage;
import io.vavr.control.Either;

import java.util.List;

public interface AccountService {
    Either<ErrorMessage, List<Account>> getAllApplyingFilter(String filters);
}
