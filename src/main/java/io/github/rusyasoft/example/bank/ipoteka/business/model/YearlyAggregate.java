package io.github.rusyasoft.example.bank.ipoteka.business.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class YearlyAggregate {
    private int year;
    private Map<String, Integer> bankAmount;

    public YearlyAggregate(int year) {
        this.year = year;
        this.bankAmount = new HashMap<>();
    }
}
