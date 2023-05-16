package com.techtrader.controller;

import com.techtrader.helper.RegisterForm;
import com.techtrader.service.TraderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/api/trader")
@CrossOrigin(origins = "*") // TODO: 2023-05-07  remove in production
public class TraderController {

    private final TraderService traderService;

    @Autowired
    public TraderController(TraderService traderService) {
        this.traderService = traderService;
    }

    @PostMapping("/register")
    public String addTrader(@Valid @RequestBody RegisterForm form) {
        return traderService.addTrader(form);
    }

    @PutMapping("/update")
    public String updateTrader(@Valid @RequestBody RegisterForm form, Principal principal) {
        return traderService.updateTrader(principal, form);
    }

}
