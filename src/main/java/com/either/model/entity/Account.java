package com.either.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account extends Audit {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  @JdbcTypeCode(SqlTypes.BIGINT)
  private Long id;

  @Column(name = "arrangement_id")
  private String arrangementId;

  @Column(name = "customer_id")
  private String customerId;

  @Column(name = "opening_date")
  private LocalDate openingDate;

  @Column(name = "active")
  private boolean active;
}
