package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@Getter
public class IncomeTax {
    private BigDecimal comprehensiveIncomeAmount;
    private BigDecimal incomeDeductionCreditCard;
    private BigDecimal incomeDeductionNationalPension;
    private BigDecimal taxCredit;

    public static IncomeTax create(BigDecimal comprehensiveIncomeAmount,
                                   BigDecimal incomeDeductionCreditCard,
                                   BigDecimal incomeDeductionNationalPension,
                                   BigDecimal taxCredit) {

        final IncomeTax incomeTax = new IncomeTax();

        incomeTax.comprehensiveIncomeAmount = comprehensiveIncomeAmount;
        incomeTax.incomeDeductionCreditCard = incomeDeductionCreditCard;
        incomeTax.incomeDeductionNationalPension = incomeDeductionNationalPension;
        incomeTax.taxCredit = taxCredit;

        return incomeTax;
    }
}
