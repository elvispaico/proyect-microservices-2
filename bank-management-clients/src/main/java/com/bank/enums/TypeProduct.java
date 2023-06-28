package com.bank.enums;

public enum TypeProduct {
    PASIVO("01"),
    ACTIVO("02");
    private final String value;

    TypeProduct(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
