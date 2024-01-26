package com.either.service;

import com.either.common.error.ErrorMessage;
import com.either.mapper.AccountMapper;
import com.either.model.entity.Account;
import com.either.repository.AccountRepository;
import com.either.repository.specifications.SpecificationBuilder;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceH2DbImpl implements AccountService {
  private final AccountRepository accountRepository;
  private final AccountMapper accountMapper;

  public Either<ErrorMessage, List<com.either.common.domain.Account>> getAllApplyingFilter(
      String filters) {
    log.debug("get all accounts with filters: {}", filters);
    try {
      List<Account> accountList;
      if (StringUtils.hasText(filters)) {
        SpecificationBuilder builder = new SpecificationBuilder();
        Pattern pattern =
            Pattern.compile(
                "(\\w+?)\\s*(=|like|<|>|>=|<=)\\s*(\\d{4}-\\d{2}-\\d{2}|\\w+?)\\s*(\\.|AND|OR)",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(filters + ".");
        boolean oneMatch = false;
        while (matcher.find()) {
          oneMatch = true;
          builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));
        }
        Specification<Account> spec = builder.build();
        accountList = oneMatch ? accountRepository.findAll(spec) : Collections.emptyList();
      } else {
        accountList = accountRepository.findAll();
      }

      Either<ErrorMessage, List<com.either.common.domain.Account>> listFromResource =
          Either.right(accountList.stream().map(accountMapper::mapModelToDomain).toList());
      log.debug("List from DB Either result are: {}", listFromResource);
      return listFromResource;
    } catch (Exception e) {
      log.error("Getting error while getting all accounts with error message: ", e);
      return Either.left(ErrorMessage.builder().error(e.getMessage()).code("GR-001").build());
    }
  }
}
