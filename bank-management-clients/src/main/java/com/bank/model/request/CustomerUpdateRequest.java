package com.bank.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerUpdateRequest {
    private String name;
    private String numDocument;
}
