package io.github.rusyasoft.example.bank.ipoteka.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ExtremePointsOfAverage {
    private String bank;
    private List<YearlyAverage> support_amount;

    public ExtremePointsOfAverage(String bank) {
        this.bank = bank;
        this.support_amount = new ArrayList<>();
    }

    public ExtremePointsOfAverage(String bankName, YearlyAverage minYearlyAverage, YearlyAverage maxYearlyAverage) {
        this.bank = bankName;
        this.support_amount = new ArrayList<>();
        this.support_amount.add(minYearlyAverage);
        this.support_amount.add(maxYearlyAverage);
    }
}
