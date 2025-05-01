package com.example.wallet.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;

    @Column(name = "operation_type")
    private String operationType;
    @Column(name = "balance")
    private BigDecimal balance;
}
