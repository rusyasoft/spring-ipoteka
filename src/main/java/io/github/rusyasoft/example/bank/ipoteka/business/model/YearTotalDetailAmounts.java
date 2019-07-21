package io.github.rusyasoft.example.bank.ipoteka.business.model;

import java.util.HashMap;
import java.util.Map;

public class YearTotalDetailAmounts implements IYearlyTotalDetailAmounts {
    @Override
    public int getYear() {
        return this.year;
    }

    @Override
    public int getTotal_amount() {
        return this.totalAmount;
    }

    @Override
    public Map<String, Integer> getDetail_amount() {
        return yearlyBankDetailAmountList;
    }

    private Map<String, Integer> yearlyBankDetailAmountList;
    private int totalAmount;
    private int year;

    public YearTotalDetailAmounts(YearlyAggregate yearlyAggregate) {

        this.year = yearlyAggregate.getYear();

        yearlyBankDetailAmountList = new HashMap<>();
        yearlyAggregate.getBankAmount().keySet().stream().forEach(bankName -> {
            int banksAmount = yearlyAggregate.getBankAmount().get(bankName);
            this.totalAmount += banksAmount;
            yearlyBankDetailAmountList.put(bankName, banksAmount);
        });

    }

}
