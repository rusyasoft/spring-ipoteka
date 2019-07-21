package io.github.rusyasoft.example.bank.ipoteka.business.service;

import io.github.rusyasoft.example.bank.ipoteka.business.model.IYearlySumAmounts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class YearlySumAmountsTest implements IYearlySumAmounts {
    private int year;
    private String name;
    private int summa;

    public void setYear(int year) {
        this.year = year;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSumma(int summa) {
        this.summa = summa;
    }

    @Override
    public int getYear() {
        return this.year;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getSumma() {
        return this.summa;
    }
}
