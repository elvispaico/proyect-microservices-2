package com.bank.model;

import com.bank.model.bean.ProductDto;
import lombok.*;

import java.util.List;


@Data
@Builder
public class CustomerProductResponse {
    private String id;
    private String name;
    private String numDocument;
    private String codTypeDocument;
    private String codTypeCustomer;
    private List<ProductDto> products;
}
