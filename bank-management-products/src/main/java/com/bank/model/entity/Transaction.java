package com.bank.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
@Document(collection = "transactions")
public class Transaction {
    @Id
    private String id;
    private String idProduct;
    private LocalDate feTransaction;
    private String codTypeTransaction;
    private String desTypeTransaction;
    private double amount;
    private double commission;
}
