package com.either.repository.specifications;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SpecSearchCriteria {
    private String key;
    private SpecSearchOperation operation;
    private Object value;
    private String fusionOperation;
}
