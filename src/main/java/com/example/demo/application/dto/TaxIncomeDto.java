package com.example.demo.application.dto;

import java.math.BigDecimal;

public record TaxIncomeDto(BigDecimal comprehensiveIncomeAmount,
                           BigDecimal incomeDeductionCreditCard,
                           BigDecimal incomeDeductionNationalPension,
                           BigDecimal taxCredit) {

}
