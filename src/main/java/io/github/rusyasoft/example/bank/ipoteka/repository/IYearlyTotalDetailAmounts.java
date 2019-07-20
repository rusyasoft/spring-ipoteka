package io.github.rusyasoft.example.bank.ipoteka.repository;

import java.util.List;
import java.util.Map;

public interface IYearlyTotalDetailAmounts {
     int getYear();
     int getTotal_amount();
     Map<String, Integer> getDetail_amount();
     //List<YearlyBankDetailAmount> getDetailAmount();
}