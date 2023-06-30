package com.bank.mapper;

import com.bank.model.bean.ProductDto;
import com.bank.model.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static List<ProductDto> mapListProductToListProductDto(List<Product> lisProducts) {
        return lisProducts.stream()
                .map(product -> {
                    return ProductDto.builder()
                            .id(product.getId())
                            .codTypeProduct(product.getCodTypeProduct())
                            .codTypeService(product.getCodTypeService())
                            .balance(product.getBalance())
                            .commission(product.getCommission())
                            .build();
                }).collect(Collectors.toList());
    }
}
