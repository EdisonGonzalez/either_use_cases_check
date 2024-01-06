package com.either.repository.specifications;

import com.either.model.entity.Account;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class SpecificationBuilder {
    private final List<SpecSearchCriteria> filterParams;

    public SpecificationBuilder() {
        filterParams = new ArrayList<>();
    }

    public final void with(String key, String operation, Object value, String fusionOperation) {
        SpecSearchOperation specSearchOperation = SpecSearchOperation.getSearchOperationByValue(operation);
        filterParams.add(new SpecSearchCriteria(key, specSearchOperation, value, fusionOperation));
    }

    public Specification<Account> build() {
        if (filterParams.isEmpty())
            return null;

        Specification<Account> result = new AccountSpecification(filterParams.get(0));

        for (int i = 1; i < filterParams.size(); i++) {
            if (filterParams.get(i-1).getFusionOperation().equalsIgnoreCase("OR")) {
                result = Specification.where(result).or(new AccountSpecification(filterParams.get(i)));
            } else if (filterParams.get(i-1).getFusionOperation().equalsIgnoreCase("AND")) {
                result = Specification.where(result).and(new AccountSpecification(filterParams.get(i)));
            }
        }
        return result;
    }
}
