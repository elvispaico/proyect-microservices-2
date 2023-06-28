package com.bank.enums;

public enum TypeService {

    SAVING("01"),
    CURRENT("02"),
    FIXEDTERM("03"),
    CRPERSONAL("04"),
    CRBUSSINESS("05"),
    CRCREDITCARD("06");

    private final String value;

    TypeService(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
