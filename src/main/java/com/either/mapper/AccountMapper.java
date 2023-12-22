package com.either.mapper;

import com.either.common.domain.Account;
import com.either.model.dto.AccountResponse;
import com.either.model.entity.DbAccount;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account mapModelToDomain(DbAccount dbAccount);
    List<Account> mapModelToDomainList(List<DbAccount> dbAccountList);
    AccountResponse mapDomainToDto(Account account);
    List<AccountResponse> mapDomainToDtoList(List<Account> accountList);
}
