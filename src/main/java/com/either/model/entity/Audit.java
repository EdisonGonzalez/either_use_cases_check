package com.either.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
public class Audit {
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDate createdDate;
}
