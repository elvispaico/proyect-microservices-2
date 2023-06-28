package com.bank.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Setter
@Getter
@Document("products")
public class Product {
    private String id;
    private String idCustomer;
    private String codTypeProduct;
    private String desTypeProduct;
    private String codTypeService;
    private String desTypeService;
    private double balance;
    private double commission;
}
