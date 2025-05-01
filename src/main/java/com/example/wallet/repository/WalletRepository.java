package com.example.wallet.repository;

import com.example.wallet.model.entity.Wallet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(propagation = Propagation.REQUIRED)
@Repository
public interface WalletRepository extends CrudRepository<Wallet, Long> {
    @Override
    Optional<Wallet> findById(Long id);
}
