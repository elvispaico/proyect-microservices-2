package com.bank.model.request;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TransferRequest {
    private double amount;
    private String date;
}
