package com.either.repository.specifications;

import com.either.model.entity.Account;
import jakarta.persistence.criteria.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class AccountSpecification implements Specification<Account> {
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private final SpecSearchCriteria criteria;

  @Override
  public Predicate toPredicate(
          @NonNull Root<Account> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
    return switch (criteria.getOperation()) {
      case GREATER_THAN -> {
        if (criteria.getKey().contains("Date")) {
          yield criteriaBuilder.greaterThan(
              root.get(criteria.getKey()),
              LocalDate.parse(criteria.getValue().toString(), DATE_FORMATTER));
        } else {
          yield criteriaBuilder.greaterThan(
              root.get(criteria.getKey()), criteria.getValue().toString());
        }
      }
      case LESS_THAN -> {
        if (criteria.getKey().contains("Date")) {
          yield criteriaBuilder.lessThan(
              root.get(criteria.getKey()),
              LocalDate.parse(criteria.getValue().toString(), DATE_FORMATTER));
        } else {
          yield criteriaBuilder.lessThan(
              root.get(criteria.getKey()), criteria.getValue().toString());
        }
      }
      case GREATER_THAN_OR_EQUAL -> {
        if (criteria.getKey().contains("Date")) {
          yield criteriaBuilder.greaterThanOrEqualTo(
              root.get(criteria.getKey()),
              LocalDate.parse(criteria.getValue().toString(), DATE_FORMATTER));
        } else {
          yield criteriaBuilder.greaterThanOrEqualTo(
              root.get(criteria.getKey()), criteria.getValue().toString());
        }
      }
      case LESS_THAN_OR_EQUAL -> {
        if (criteria.getKey().contains("Date")) {
          yield criteriaBuilder.lessThanOrEqualTo(
              root.get(criteria.getKey()),
              LocalDate.parse(criteria.getValue().toString(), DATE_FORMATTER));
        } else {
          yield criteriaBuilder.lessThanOrEqualTo(
              root.get(criteria.getKey()), criteria.getValue().toString());
        }
      }
      case LIKE ->
          criteriaBuilder.like(
              root.get(criteria.getKey()), String.format("%%%s%%", criteria.getValue().toString()));
      case EQUAL ->
          criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue().toString());
    };
  }
}
