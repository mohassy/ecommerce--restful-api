package com.techtrader.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Cart {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @OneToOne
    @OnDelete( action = OnDeleteAction.CASCADE)
    private Trader trader;

    @ManyToMany(cascade = CascadeType.REMOVE)
    private Set<Device> devices = new HashSet<>();

}
