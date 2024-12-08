package com.example.demo.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TaxManagement {

    public static BigDecimal calculateDeterminedTaxAmount(BigDecimal comprehensiveIncomeAmount, BigDecimal incomeDeductionCreditCard, BigDecimal incomeDeductionNationalPension, BigDecimal taxCredit) {
        BigDecimal taxBase = comprehensiveIncomeAmount.subtract(incomeDeductionCreditCard).subtract(incomeDeductionNationalPension);
        BigDecimal result;
        if (taxBase.compareTo(BigDecimal.valueOf(14000000)) <= 0) {
            result = taxBase.multiply(BigDecimal.valueOf(0.06)).setScale(0, RoundingMode.HALF_UP);
        } else if (taxBase.compareTo(BigDecimal.valueOf(50000000)) <= 0) {
            result = BigDecimal.valueOf(840000).add(
                    taxBase.subtract(BigDecimal.valueOf(14000000)).multiply(BigDecimal.valueOf(0.15))
            ).setScale(0, RoundingMode.HALF_UP);
        } else if (taxBase.compareTo(BigDecimal.valueOf(88000000)) <= 0) {
            result = BigDecimal.valueOf(6240000).add(
                    taxBase.subtract(BigDecimal.valueOf(50000000)).multiply(BigDecimal.valueOf(0.24))
            ).setScale(0, RoundingMode.HALF_UP);
        } else if (taxBase.compareTo(BigDecimal.valueOf(150000000)) <= 0) {
            result = BigDecimal.valueOf(15360000).add(
                    taxBase.subtract(BigDecimal.valueOf(88000000)).multiply(BigDecimal.valueOf(0.35))
            ).setScale(0, RoundingMode.HALF_UP);
        } else if (taxBase.compareTo(BigDecimal.valueOf(300000000)) <= 0) {
            result = BigDecimal.valueOf(37060000).add(
                    taxBase.subtract(BigDecimal.valueOf(150000000)).multiply(BigDecimal.valueOf(0.38))
            ).setScale(0, RoundingMode.HALF_UP);
        } else if (taxBase.compareTo(BigDecimal.valueOf(500000000)) <= 0) {
            result = BigDecimal.valueOf(94060000).add(
                    taxBase.subtract(BigDecimal.valueOf(300000000)).multiply(BigDecimal.valueOf(0.40))
            ).setScale(0, RoundingMode.HALF_UP);
        } else if (taxBase.compareTo(BigDecimal.valueOf(1000000000)) <= 0) {
            result = BigDecimal.valueOf(174060000).add(
                    taxBase.subtract(BigDecimal.valueOf(500000000)).multiply(BigDecimal.valueOf(0.42))
            ).setScale(0, RoundingMode.HALF_UP);
        } else {
            result = BigDecimal.valueOf(384060000).add(
                    taxBase.subtract(BigDecimal.valueOf(1000000000)).multiply(BigDecimal.valueOf(0.45))
            ).setScale(0, RoundingMode.HALF_UP);
        }

        return result.subtract(taxCredit).setScale(0, RoundingMode.HALF_UP);
    }
}
