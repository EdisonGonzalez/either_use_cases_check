package com.either.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AccountResponse {
    private String id;
    private String arrangementId;
    private String customerId;
    private LocalDate openingDate;
}
