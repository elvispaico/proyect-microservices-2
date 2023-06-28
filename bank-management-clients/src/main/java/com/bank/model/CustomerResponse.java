package com.bank.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponse {
    private String id;
    private String name;
    private String numDocument;
    private String codTypeCustomer;
    private String desTypeCustomer;
}
