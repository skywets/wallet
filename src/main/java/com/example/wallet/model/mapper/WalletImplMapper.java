package com.example.wallet.model.mapper;

import com.example.wallet.model.dto.WalletDto;
import com.example.wallet.model.entity.Wallet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class WalletImplMapper implements MapperDto<WalletDto, Wallet, WalletDto> {
    @Override
    public WalletDto mapToDto(Wallet entity) {
        WalletDto walletDto = new WalletDto();
        walletDto.setWalletId(entity.getWalletId());
        walletDto.setOperationType(entity.getOperationType());
        walletDto.setAmount(entity.getBalance());
        return walletDto;
    }

    @Override
    public Wallet mapToEntity(WalletDto dto) {
        Wallet wallet = new Wallet();
        wallet.setWalletId(dto.getWalletId());
        wallet.setOperationType(dto.getOperationType());
        wallet.setBalance(dto.getAmount());
        return wallet;
    }

    @Override
    public List<WalletDto> maptoDto(Iterable<Wallet> entities) {
        List<WalletDto> walletDtos = new ArrayList<>();
        for (Wallet wallet : entities) {
            walletDtos.add(mapToDto(wallet));
        }
        return walletDtos;
    }

    @Override
    public List<Wallet> mapToEntity(Iterable<WalletDto> dtos) {
        List<Wallet> wallets = new ArrayList<>();
        for (WalletDto walletDto : dtos) {
            wallets.add(mapToEntity(walletDto));
        }
        return wallets;
    }
}
