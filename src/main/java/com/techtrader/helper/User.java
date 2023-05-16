package com.techtrader.helper;

import com.techtrader.model.Trader;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Trader user;
    private String token;
}
