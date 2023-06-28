package com.bank.util;

import com.bank.enums.TypeService;

public class BussinessLogic {

    public static boolean isCustomerPersonalVip(String codTypeCustomer, String codPerfil) {
        if (codTypeCustomer == null || codPerfil == null) return false;
        return codTypeCustomer.equals("01") && codPerfil.equals("02");
    }

    public static boolean isServiceAccountSaving(String codService) {
        return codService.equals(TypeService.SAVING.getValue());
    }
}
