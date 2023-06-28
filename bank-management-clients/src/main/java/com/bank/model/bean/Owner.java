package com.bank.model.bean;

import lombok.Data;

/**
 * Clase Owner representa el ciente
 * contiene los campos basicos
 */
@Data
public class Owner {
    private String id;
    private String name;
    private String codTypeCustomer;
    private String numDocument;
    private String codTypeDocument;
}
