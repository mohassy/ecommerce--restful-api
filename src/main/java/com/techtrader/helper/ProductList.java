package com.techtrader.helper;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class ProductList {
    private List<Product> products = new ArrayList<>();
}
