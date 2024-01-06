package com.either.mapper;

import com.either.model.dto.AccountResponse;
import com.either.model.entity.Account;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    com.either.common.domain.Account mapModelToDomain(Account account);
    AccountResponse mapDomainToDto(com.either.common.domain.Account account);
    List<AccountResponse> mapDomainToDtoList(List<com.either.common.domain.Account> accountList);
}
