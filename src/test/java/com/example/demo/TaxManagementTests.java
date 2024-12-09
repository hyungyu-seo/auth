package com.example.demo;

import com.example.demo.domain.TaxManagement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaxManagementTests {

    @Test
    @DisplayName("결정세액 단위 테스트 1400만원 이하")
    public void calculateDeterminedTaxAmount_under1400() {

        // given
        final BigDecimal comprehensiveIncomeAmount = BigDecimal.valueOf(14000000);
        final BigDecimal incomeDeductionCreditCard = BigDecimal.valueOf(0);
        final BigDecimal incomeDeductionNationalPension = BigDecimal.valueOf(0);
        final BigDecimal taxCredit = BigDecimal.valueOf(0);

        final BigDecimal expectedTax = BigDecimal.valueOf(840000);

        // When
        final BigDecimal result = TaxManagement.calculateDeterminedTaxAmount(comprehensiveIncomeAmount, incomeDeductionCreditCard, incomeDeductionNationalPension, taxCredit);

        // Then
        assertEquals(expectedTax, result);
    }

    @Test
    @DisplayName("결정세액 단위 테스트 5000만원 이하")
    public void calculateDeterminedTaxAmount_under5000() {

        // given
        final BigDecimal comprehensiveIncomeAmount = BigDecimal.valueOf(50100000);
        final BigDecimal incomeDeductionCreditCard = BigDecimal.valueOf(50000);
        final BigDecimal incomeDeductionNationalPension = BigDecimal.valueOf(50000);
        final BigDecimal taxCredit = BigDecimal.valueOf(40000);

        final BigDecimal expectedTax = BigDecimal.valueOf(6200000);

        // When
        final BigDecimal result = TaxManagement.calculateDeterminedTaxAmount(comprehensiveIncomeAmount, incomeDeductionCreditCard, incomeDeductionNationalPension, taxCredit);

        // Then
        assertEquals(expectedTax, result);
    }

    @Test
    @DisplayName("결정세액 단위 테스트 8800만원 이하")
    public void calculateDeterminedTaxAmount_under8800() {

        // given
        final BigDecimal comprehensiveIncomeAmount = BigDecimal.valueOf(88000000);
        final BigDecimal incomeDeductionCreditCard = BigDecimal.valueOf(0);
        final BigDecimal incomeDeductionNationalPension = BigDecimal.valueOf(0);
        final BigDecimal taxCredit = BigDecimal.valueOf(0);

        final BigDecimal expectedTax = BigDecimal.valueOf(15360000);

        // When
        final BigDecimal result = TaxManagement.calculateDeterminedTaxAmount(comprehensiveIncomeAmount, incomeDeductionCreditCard, incomeDeductionNationalPension, taxCredit);

        // Then
        assertEquals(expectedTax, result);
    }

    @Test
    @DisplayName("결정세액 단위 테스트 15000만원 이하")
    public void calculateDeterminedTaxAmount_under15000() {

        // given
        final BigDecimal comprehensiveIncomeAmount = BigDecimal.valueOf(150000000);
        final BigDecimal incomeDeductionCreditCard = BigDecimal.valueOf(0);
        final BigDecimal incomeDeductionNationalPension = BigDecimal.valueOf(0);
        final BigDecimal taxCredit = BigDecimal.valueOf(0);

        final BigDecimal expectedTax = BigDecimal.valueOf(37060000);

        // When
        final BigDecimal result = TaxManagement.calculateDeterminedTaxAmount(comprehensiveIncomeAmount, incomeDeductionCreditCard, incomeDeductionNationalPension, taxCredit);

        // Then
        assertEquals(expectedTax, result);
    }

    @Test
    @DisplayName("결정세액 단위 테스트 30000만원 이하")
    public void calculateDeterminedTaxAmount_under30000() {

        // given
        final BigDecimal comprehensiveIncomeAmount = BigDecimal.valueOf(300000000);
        final BigDecimal incomeDeductionCreditCard = BigDecimal.valueOf(0);
        final BigDecimal incomeDeductionNationalPension = BigDecimal.valueOf(0);
        final BigDecimal taxCredit = BigDecimal.valueOf(0);

        final BigDecimal expectedTax = BigDecimal.valueOf(94060000);

        // When
        final BigDecimal result = TaxManagement.calculateDeterminedTaxAmount(comprehensiveIncomeAmount, incomeDeductionCreditCard, incomeDeductionNationalPension, taxCredit);

        // Then
        assertEquals(expectedTax, result);
    }

    @Test
    @DisplayName("결정세액 단위 테스트 50000만원 이하")
    public void calculateDeterminedTaxAmount_under50000() {

        // given
        final BigDecimal comprehensiveIncomeAmount = BigDecimal.valueOf(500000000);
        final BigDecimal incomeDeductionCreditCard = BigDecimal.valueOf(0);
        final BigDecimal incomeDeductionNationalPension = BigDecimal.valueOf(0);
        final BigDecimal taxCredit = BigDecimal.valueOf(0);

        final BigDecimal expectedTax = BigDecimal.valueOf(174060000);

        // When
        final BigDecimal result = TaxManagement.calculateDeterminedTaxAmount(comprehensiveIncomeAmount, incomeDeductionCreditCard, incomeDeductionNationalPension, taxCredit);

        // Then
        assertEquals(expectedTax, result);
    }

    @Test
    @DisplayName("결정세액 단위 테스트 100000만원 이하")
    public void calculateDeterminedTaxAmount_under100000() {

        // given
        final BigDecimal comprehensiveIncomeAmount = BigDecimal.valueOf(1000000000);
        final BigDecimal incomeDeductionCreditCard = BigDecimal.valueOf(0);
        final BigDecimal incomeDeductionNationalPension = BigDecimal.valueOf(0);
        final BigDecimal taxCredit = BigDecimal.valueOf(0);

        final BigDecimal expectedTax = BigDecimal.valueOf(384060000);

        // When
        final BigDecimal result = TaxManagement.calculateDeterminedTaxAmount(comprehensiveIncomeAmount, incomeDeductionCreditCard, incomeDeductionNationalPension, taxCredit);

        // Then
        assertEquals(expectedTax, result);
    }

    @Test
    @DisplayName("결정세액 단위 테스트 100000만원 초과")
    public void calculateDeterminedTaxAmount_over_100000() {

        // given
        final BigDecimal comprehensiveIncomeAmount = BigDecimal.valueOf(1500000000);
        final BigDecimal incomeDeductionCreditCard = BigDecimal.valueOf(0);
        final BigDecimal incomeDeductionNationalPension = BigDecimal.valueOf(0);
        final BigDecimal taxCredit = BigDecimal.valueOf(60000);

        final BigDecimal expectedTax = BigDecimal.valueOf(609000000);

        // When
        final BigDecimal result = TaxManagement.calculateDeterminedTaxAmount(comprehensiveIncomeAmount, incomeDeductionCreditCard, incomeDeductionNationalPension, taxCredit);

        // Then
        assertEquals(expectedTax, result);
    }
}
