package io.github.rusyasoft.example.bank.ipoteka.business.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FinanceStatPredictResponse {
    private String bank;
    private int year;
    private int month;
    private int amount;
}
