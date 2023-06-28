package com.bank.enums;

public enum TypeCustomer {

    PERSONAL("01"),
    BUSSINESS("02");
    private final String value;

    TypeCustomer(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
