package com.tongbora.accountservice.data.entity;

import com.tongbora.common.BranchId;
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
