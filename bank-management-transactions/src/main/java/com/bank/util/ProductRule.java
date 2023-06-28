package com.bank.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ProductRule {
    private int numTrans;
    private int limitMaxTrans;
    private double commision;
    private int numTransFree;
}
