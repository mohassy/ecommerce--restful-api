package com.techtrader.model;

import com.techtrader.helper.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Transaction {

    @Id @GeneratedValue
    private Long id;


    @OneToOne
    @OnDelete( action = OnDeleteAction.CASCADE)
    private Trader trader;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "transaction_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Order> orders = new HashSet<>();

    private double total;
    private Status status;

    @PrePersist
    public void calculateTotal(){
        total = orders.stream()
                .mapToDouble(order-> order
                        .getListing()
                        .getDevice()
                        .getPrice() * order.getQuantity())
                .sum();
    }

}
