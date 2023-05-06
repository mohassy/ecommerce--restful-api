package com.techtrader.service;

import com.techtrader.model.Cart;
import com.techtrader.model.Trader;
import com.techtrader.repository.CartRepository;
import com.techtrader.repository.DeviceRepository;
import com.techtrader.repository.TraderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final DeviceRepository deviceRepository;
    private final TraderRepository traderRepository;

    public CartService(CartRepository cartRepository, DeviceRepository deviceRepository, TraderRepository traderRepository) {
        this.cartRepository = cartRepository;
        this.deviceRepository = deviceRepository;
        this.traderRepository = traderRepository;
    }
    public Cart getCart(Principal principal) {
        Optional<Cart> cart = cartRepository.findByTraderUsername(principal.getName());
        if(cart.isPresent()){
            return cart.get();
        }else{
            Trader trader = traderRepository.findByUsername(principal.getName()).orElseThrow();
            return Cart.builder()
                    .trader(trader)
                    .build();
        }
    }


    @Transactional
    public String removeDevice(Principal principal, Long deviceId) {
        Cart cart = cartRepository.findByTraderUsername(principal.getName())
                .orElseThrow(() -> new NoSuchElementException("Cart for: "+principal.getName()+" doesn't exist"));
        cart.getDevices().parallelStream().forEach(d -> {if(d.getId() == deviceId) cart.getDevices().remove(d);});
        return "success";

    }

    @Transactional
    public String addDevice(Principal principal, Long deviceId) {
        Cart cart = cartRepository.findByTraderUsername(principal.getName())
                .orElseThrow(() -> new NoSuchElementException("Cart for: "+principal.getName()+" doesn't exist"));
        cart.getDevices().add(
                deviceRepository.findById(deviceId).orElseThrow()
        );
        return "success";
    }
}
