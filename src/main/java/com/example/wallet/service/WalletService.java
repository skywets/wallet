package com.example.wallet.service;

import com.example.wallet.model.dto.WalletDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WalletService {
    void create(WalletDto walletDto);

    void updateBalance(WalletDto walletDto);

    void delete(Long id);

    WalletDto findByid(Long id);

    List<WalletDto> findAll();
}
