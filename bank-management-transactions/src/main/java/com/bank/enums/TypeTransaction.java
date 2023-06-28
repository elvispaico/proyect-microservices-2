package com.bank.enums;

public enum TypeTransaction {
    DEPOSITO("01"),
    RETIRO("02");
    private final String value;

    TypeTransaction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;

    }
}