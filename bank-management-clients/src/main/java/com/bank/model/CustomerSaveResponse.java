package com.bank.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerSaveResponse {
    private String fullname;
    private String numDocumet;
    private String email;
}
