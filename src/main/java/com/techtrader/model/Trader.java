package com.techtrader.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nimbusds.jose.shaded.gson.JsonElement;
import com.techtrader.helper.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Trader{
    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String username;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    @JsonIgnore
    private List<Role> roles;

}
