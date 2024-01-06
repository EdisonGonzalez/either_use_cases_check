package com.either.repository.specifications;

import com.either.model.entity.Account;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class AccountSpecification implements Specification<Account> {
  private final SpecSearchCriteria criteria;

  @Override
  public Predicate toPredicate(
      Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    return switch (criteria.getOperation()) {
      case GREATER_THAN -> criteriaBuilder.greaterThan(
          root.get(criteria.getKey()), criteria.getValue().toString());
      case LESS_THAN -> criteriaBuilder.lessThan(
          root.get(criteria.getKey()), criteria.getValue().toString());
      case GREATER_THAN_OR_EQUAL -> criteriaBuilder.greaterThanOrEqualTo(
          root.get(criteria.getKey()), criteria.getValue().toString());
      case LESS_THAN_OR_EQUAL -> criteriaBuilder.lessThanOrEqualTo(
          root.get(criteria.getKey()), criteria.getValue().toString());
      case LIKE -> criteriaBuilder.like(
          root.get(criteria.getKey()), String.format("%%%s%%", criteria.getValue().toString()));
      case EQUAL -> criteriaBuilder.equal(
          root.get(criteria.getKey()), criteria.getValue().toString());
    };
  }
}
