package com.techtrader.service;

import com.techtrader.helper.RegisterForm;
import com.techtrader.helper.Role;
import com.techtrader.model.Trader;
import com.techtrader.repository.TraderRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class TraderService {
    private final TraderRepository traderRepository;
    private final PasswordEncoder passwordEncoder;

    public TraderService(TraderRepository traderRepository, PasswordEncoder passwordEncoder) {
        this.traderRepository = traderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String addTrader(RegisterForm form) {
        try {
            Trader trader = Trader.builder()
                    .email(form.getEmail())
                    .username(form.getUsername())
                    .firstName(form.getFirstName())
                    .lastName(form.getLastName())
                    .roles(List.of(Role.TRADER))
                    .password(passwordEncoder.encode(form.getPassword()))
                    .build();
            traderRepository.save(trader);
            return "success";
        } catch (Exception e) {
            return "username or email already exists" + e.getMessage();
        }

    }

    @Transactional
    public String updateTrader(Principal principal, RegisterForm form) {
        Trader trader = traderRepository.findByUsername(principal.getName()).orElseThrow();
        trader.setEmail(form.getEmail());
        trader.setUsername(form.getUsername());
        trader.setPassword(passwordEncoder.encode(form.getPassword()));
        trader.setFirstName(form.getFirstName());
        trader.setLastName(form.getLastName());
        return "success";
    }


    public Trader getTrader(String userName) {
        return traderRepository.findByUsername(userName).orElseThrow();
    }
}
