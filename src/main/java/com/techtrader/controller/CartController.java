package com.techtrader.controller;

import com.techtrader.model.Cart;
import com.techtrader.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*") // TODO: 2023-05-07  remove in production
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping()
    public Cart getCart(Principal principal) {
        return cartService.getCart(principal);
    }

    @PutMapping("/remove/{device_id}")
    public String removeDevice(Principal principal, @PathVariable Long device_id) {
        return cartService.removeDevice(principal, device_id);
    }

    @PutMapping("/add/{device_id}")
    public String addDevice(Principal principal, @PathVariable Long device_id) {
        return cartService.addDevice(principal, device_id);
    }

}
