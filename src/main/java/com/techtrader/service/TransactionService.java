package com.techtrader.service;

import com.techtrader.helper.Product;
import com.techtrader.helper.ProductList;
import com.techtrader.helper.Status;
import com.techtrader.model.*;
import com.techtrader.repository.*;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final OrderRepository orderRepository;
    private final TraderRepository traderRepository;
    private final ListingRepository listingRepository;
    private final DeviceRepository deviceRepository;

    public TransactionService(TransactionRepository transactionRepository, OrderRepository orderRepository, TraderRepository traderRepository, ListingRepository listingRepository, DeviceRepository deviceRepository) {
        this.transactionRepository = transactionRepository;
        this.orderRepository = orderRepository;
        this.traderRepository = traderRepository;
        this.listingRepository = listingRepository;
        this.deviceRepository = deviceRepository;
    }

    public List<Transaction> getTransactions(Principal principal) {
        return transactionRepository
                .findALLByTraderUsername(principal.getName())
                .orElseThrow();
    }

    public String buyProducts(Principal principal, ProductList productList) {
        Trader trader = traderRepository.findByUsername(principal.getName()).orElseThrow();
        // map product to listing in database, for each listing create and order
        List<Product> products = productList.getProducts();
        List<Device> devices = new ArrayList<>(products.size());
        Set<Order> orders = products
                .parallelStream()
                .distinct()
                .map(p -> {
                    // get listing
                    Listing listing = listingRepository.findByDeviceId(p.getId()).orElseThrow();
                    // make and save order;
                    Order o = Order
                            .builder()
                            .listing(listing)
                            .quantity(p.getQuantity())
                            .build();
                    // update device stock;
                    Device d = deviceRepository.findById(p.getId()).orElseThrow();
                    int newStock = d.getStock() - p.getQuantity();
                    if (newStock < 0) {
                        throw new IllegalStateException("order: "
                                + p.getId() + " has:  " + p.getQuantity()
                                + " when device: " + d.getId()
                                + " has stock: " + d.getStock());
                    }
                    d.setStock(newStock);
                    devices.add(d);
                    return o;
                })
                .collect(Collectors.toSet());
        //save updated device and new orders
        devices.parallelStream().forEach(deviceRepository::save);
        orders.parallelStream().forEach(orderRepository::save);
        // create a transaction
        Transaction transaction = Transaction
                .builder()
                .orders(orders)
                .trader(trader)
                .status(Status.PENDING)
                .build();
        transactionRepository.save(transaction);
        return "success";
    }
}
