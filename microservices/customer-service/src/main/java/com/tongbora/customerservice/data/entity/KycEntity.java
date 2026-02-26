package com.tongbora.customerservice.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "kyc")
public class KycEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID kycId;

    private String type;
    private String number;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

}
