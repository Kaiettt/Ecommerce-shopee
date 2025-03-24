package com.ak.ecommerce_vender.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ak.ecommerce_vender.domain.entity.ConfirmationToken;
import com.ak.ecommerce_vender.repository.ConfirmationTokenRepository;
@Service
public class ConfirmationService {
    private final ConfirmationTokenRepository confirmationTokenRepository;    

    public ConfirmationService(ConfirmationTokenRepository confirmationTokenRepository){
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public Optional<ConfirmationToken> getConfirmationByTokenAndEmail(long token,String email){
        return this.confirmationTokenRepository.findByTokenAndUserEmail(token,email);
    }

    public void ConfirmToken(long tokenNumber,String email) {
        this.confirmationTokenRepository.updateConfirmedAt(tokenNumber, LocalDateTime.now(),email);
    }
}
