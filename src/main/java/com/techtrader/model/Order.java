package com.techtrader.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "purchase_order")
public class Order {

    @Id @GeneratedValue
    private Long id;

    @OneToOne
    @OnDelete( action = OnDeleteAction.CASCADE)
    private Listing listing;

    private Date dateOrdered;
    private int quantity;


}
