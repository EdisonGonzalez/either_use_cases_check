package com.either.repository.specifications;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SpecSearchOperation {
    GREATER_THAN(">"),
    LESS_THAN("<"),
    GREATER_THAN_OR_EQUAL(">="),
    LESS_THAN_OR_EQUAL("<="),
    LIKE("like"),
    EQUAL("=");

    private final String value;

    public static SpecSearchOperation getSearchOperationByValue(String value) {
        for (SpecSearchOperation operation : SpecSearchOperation.values()) {
            if (operation.getValue().equals(value)) {
                return operation;
            }
        }
        throw new IllegalArgumentException("Operation not found: " + value);
    }
}
