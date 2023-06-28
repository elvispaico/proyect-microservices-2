package com.bank.util;

public class BussinessRules {

    private static ProductRule getRuleAhorro() {
        return ProductRule.builder()
                .numTrans(10)
                .limitMaxTrans(10)
                .commision(0.0)
                .numTransFree(20)
                .build();
    }

    private static ProductRule getRuleCuentaCorriente() {
        return ProductRule.builder()
                .numTrans(10000)
                .limitMaxTrans(10000)
                .commision(10.0)
                .numTransFree(20)
                .build();
    }

    private static ProductRule getRulePazoFijo() {
        return ProductRule.builder()
                .numTrans(1)
                .limitMaxTrans(1)
                .commision(0.0)
                .numTransFree(20)
                .build();
    }


    public static ProductRule getRule(String typeService) {
        boolean response = false;

        switch (typeService) {
            case "01":
                return getRuleAhorro();
            case "02":
                return getRuleCuentaCorriente();
            case "03":
                return getRulePazoFijo();

        }
        return null;
    }
}
