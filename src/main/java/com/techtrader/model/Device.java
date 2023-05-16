package com.techtrader.model;

import com.techtrader.helper.Color;
import com.techtrader.helper.Condition;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Device {

    @Id @GeneratedValue
    private Long id;
    private String title;
    private String img;
    private String type;
    private double price;
    private int stock;

    @ElementCollection(targetClass = com.techtrader.helper.Condition.class)
    @JoinColumn(name = "device_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Condition> conditions;

    @ElementCollection(targetClass = com.techtrader.helper.Color.class)
    @JoinColumn(name = "device_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Color> colors;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<String> specs;

}
