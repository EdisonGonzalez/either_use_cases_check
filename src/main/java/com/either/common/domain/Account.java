package com.either.common.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Account {
    private String id;
    private String arrangementId;
    private String customerId;
    private LocalDate openingDate;
    private boolean active;
}
