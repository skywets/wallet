package com.example.wallet.service;

import com.example.wallet.exception.IncorrectDataEntryException;
import com.example.wallet.exception.NotFoundException;
import com.example.wallet.model.dto.WalletDto;
import com.example.wallet.model.entity.Wallet;
import com.example.wallet.model.mapper.MapperDto;
import com.example.wallet.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("walletService")
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {

    private WalletRepository walletRepository;
    private MapperDto<WalletDto, Wallet, WalletDto> mapperDto;

    @Override
    public void create(WalletDto walletDto) {
        Wallet wallet = new Wallet();
        wallet.setWalletId(walletDto.getWalletId());
        if ("DEPOSIT".equals(walletDto.getOperationType()) || "WITHDRAW".equals(walletDto.getOperationType())) {
            wallet.setOperationType(walletDto.getOperationType());
        } else {
            throw new IncorrectDataEntryException("Invalid operation type");
        }

        if (walletDto.getAmount().intValue() > 0) {
            wallet.setBalance(walletDto.getAmount());
        } else {
            throw new IncorrectDataEntryException("Minimum transfer amount 1$");
        }
        walletRepository.save(wallet);
    }


    @Transactional
    @Override
    public void updateBalance(WalletDto walletDto) {
        Wallet wallet = getByIdOrElseThrow(walletDto.getWalletId());
        if ("DEPOSIT".equals(walletDto.getOperationType())) {
            wallet.setBalance(wallet.getBalance().add(walletDto.getAmount()));
        } else if ("WITHDRAW".equals(walletDto.getOperationType())) {
            if (wallet.getBalance().compareTo(walletDto.getAmount()) < 0) {
                throw new IncorrectDataEntryException("Insufficient funds");
            }
            wallet.setBalance(wallet.getBalance().subtract(walletDto.getAmount()));
        } else {
            throw new IncorrectDataEntryException("Invalid operation type");
        }
        walletRepository.save(wallet);
    }


    @Override
    public void delete(Long id) {
        walletRepository.deleteById(id);
    }

    @Override
    public WalletDto findByid(Long id) {
        return mapperDto.mapToDto(getByIdOrElseThrow(id));
    }

    @Override
    public List<WalletDto> findAll() {
        return mapperDto.maptoDto(walletRepository.findAll());
    }


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Wallet getByIdOrElseThrow(Long id) {
        return walletRepository.findById(id).orElseThrow(
                () -> new NotFoundException("The Wallet doesn't exist"));
    }
}
