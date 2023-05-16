package com.techtrader.controller;

import com.techtrader.helper.ProductList;
import com.techtrader.model.Transaction;
import com.techtrader.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@CrossOrigin(origins = "*") // TODO: 2023-05-07  remove in production
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Transaction> getTransactions(Principal principal) {
        return transactionService.getTransactions(principal);
    }

    @PostMapping
    public String buyProducts(Principal principal, @RequestBody ProductList productList){
        return transactionService.buyProducts(principal, productList);
    }
}
