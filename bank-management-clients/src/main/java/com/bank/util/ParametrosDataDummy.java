package com.bank.util;

import com.bank.model.bean.ParameterDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParametrosDataDummy {

    private static final Map<String, List<ParameterDetail>> data = new HashMap<>();

    static {
        data.put("01", List.of(
           new ParameterDetail("01","Personal"),
           new ParameterDetail("02","Empresarial")
        ));
        data.put("02", List.of(
                new ParameterDetail("01", "Cuenta Ahorro"),
                new ParameterDetail("02", "Cuenta Corriente"),
                new ParameterDetail("03", "Cuenta Plazo Fijo"),
                new ParameterDetail("04", "Credito Personal"),
                new ParameterDetail("05", "Credito Empresarial"),
                new ParameterDetail("06", "Tarjeta Credito"))
        );
    }

    public static Map<String, List<ParameterDetail>> getData() {
        return data;
    }
}
