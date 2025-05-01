package com.example.wallet.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletDto {
    private Long walletId;
    @NotBlank
    private String operationType;
    @NotNull
    private BigDecimal amount;
}
