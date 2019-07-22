package io.github.rusyasoft.example.bank.ipoteka.business.model;

import java.util.Map;

public interface IYearlyTotalDetailAmounts {
    int getYear();

    int getTotal_amount();

    Map<String, Integer> getDetail_amount();
    //List<YearlyBankDetailAmount> getDetailAmount();
}