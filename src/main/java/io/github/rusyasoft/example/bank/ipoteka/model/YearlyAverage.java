package io.github.rusyasoft.example.bank.ipoteka.model;

import io.github.rusyasoft.example.bank.ipoteka.repository.IYearlyAverage;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class YearlyAverage {
     private int year;
     private int amount;

     public YearlyAverage(IYearlyAverage iYearlyAverage) {
          this.year = iYearlyAverage.getYear();
          this.amount = iYearlyAverage.getAverage();
     }

     public void setYearlyAverage(IYearlyAverage iYearlyAverage) {
          this.year = iYearlyAverage.getYear();
          this.amount = iYearlyAverage.getAverage();
     }
}
