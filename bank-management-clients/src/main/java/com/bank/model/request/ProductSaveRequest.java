package com.bank.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductSaveRequest {
    private String idCustomer;
    private String codTypeProduct;
    private String codTypeService;
    private double balance;
    private double commission;
}
