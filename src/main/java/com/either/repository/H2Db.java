package com.either.repository;

import com.either.common.domain.Account;
import com.either.common.error.ErrorMessage;
import com.either.mapper.AccountMapper;
import com.either.model.entity.DbAccount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
@Slf4j
public class H2Db implements AccountRepository {
  private final Gson gson;
  private final AccountMapper accountMapper;

  public Either<ErrorMessage, List<Account>> getAll(String resourceName) {
    log.trace("get all accounts");
    Either<ErrorMessage, List<Account>> listFromResource =
        getListFromResource(resourceName.concat(".json")).map(accountMapper::mapModelToDomainList);
    log.debug("List from resource Either result are: {}", listFromResource);
    return listFromResource;
  }

  private Either<ErrorMessage, List<DbAccount>> getListFromResource(String filename) {
    try (InputStreamReader reader =
        new InputStreamReader(
            Objects.requireNonNull(this.getClass().getResourceAsStream("/".concat(filename))))) {
      List<DbAccount> elements =
          gson.fromJson(reader, new TypeToken<List<DbAccount>>() {}.getType());
      return Either.right(elements);
    } catch (Exception e) {
      e.printStackTrace();
      return Either.left(ErrorMessage.builder().error(e.getMessage()).code("GR-001").build());
    }
  }
}
