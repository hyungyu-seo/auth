package com.example.demo.domain;

import com.example.demo.application.dto.TaxIncomeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Component
public class TaxManagement {

    public static TaxIncomeDto getTaxIncome(JSONObject jsonObject) throws JSONException, JsonProcessingException {

        JSONObject data = jsonObject.getJSONObject("data");

        // 1년치 국민연금 공제액 총합
        JSONArray incomeDeductionNationalPensionArray = data.getJSONObject("소득공제").getJSONArray("국민연금");
        BigDecimal incomeDeductionNationalPension = BigDecimal.valueOf(0);
        for(int i = 0; i < incomeDeductionNationalPensionArray.length(); i++) {
            String value = incomeDeductionNationalPensionArray.getJSONObject(i).get("공제액").toString();
            incomeDeductionNationalPension = incomeDeductionNationalPension.add(convert(value.replaceAll(",", "")));
        }

        // 1년치 신용카드 공제액 총합
        JSONArray incomeDeductionCreditCardArray = data.getJSONObject("소득공제").getJSONObject("신용카드소득공제").getJSONArray("month");
        BigDecimal incomeDeductionCreditCard = BigDecimal.valueOf(0);
        for(int i = 0; i < incomeDeductionCreditCardArray.length(); i++) {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<Map<String, String>> typeReference = new TypeReference<Map<String,String>>() {};

            Map<String,String> valueMap = objectMapper.readValue(incomeDeductionCreditCardArray.getJSONObject(i).toString(), typeReference);;
            incomeDeductionCreditCard = incomeDeductionCreditCard.add(convert(valueMap.values().stream().toList().get(0).replaceAll(",", "")));
        }

        // 1년치 종합소득금액
        BigDecimal comprehensiveIncomeAmount = convert(data.getString("종합소득금액"));
        // 1년치 세액공제 금액
        BigDecimal taxCredit = convert(data.getJSONObject("소득공제").getString("세액공제").replaceAll(",", ""));


        return new TaxIncomeDto(comprehensiveIncomeAmount, incomeDeductionCreditCard, incomeDeductionNationalPension, taxCredit);

    }

    public static BigDecimal calculateTaxAmount(BigDecimal comprehensiveIncomeAmount, BigDecimal incomeDeductionCreditCard, BigDecimal incomeDeductionNationalPension, BigDecimal taxCredit) {
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

    public static BigDecimal convert(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("입력 값이 null 또는 비어 있습니다.");
        }

        try {
            return new BigDecimal(value.trim().replace(",", ""));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("잘못된 숫자 형식입니다: " + value, e);
        }
    }
}
