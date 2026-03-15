package com.tongbora.accountservice.dataaccess.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "branches")
public class BranchEntity {

    @Id
    private UUID branchId;
    private String name;
    private Boolean isOpening;

}
