package com.bank.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class TransactionResponse {
    private String id;
    private String idProduct;
    private LocalDate feTransaction;
    private String codTypeTransaction;
    private String desTypeTransaction;
    private double amount;
    private double commission;
}
