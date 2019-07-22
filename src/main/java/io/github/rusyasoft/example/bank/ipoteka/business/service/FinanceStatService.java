package io.github.rusyasoft.example.bank.ipoteka.business.service;


import io.github.rusyasoft.example.bank.ipoteka.business.model.*;
import io.github.rusyasoft.example.bank.ipoteka.business.repository.BankRepository;
import io.github.rusyasoft.example.bank.ipoteka.business.repository.FinanceStatRepository;
import io.github.rusyasoft.example.bank.ipoteka.prediction.ArimaWrapper;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FinanceStatService {

    @Autowired
    public FinanceStatRepository financeStatRepository;

    @Autowired
    public BankRepository bankRepository;

    public List<IYearlyTotalDetailAmounts> getYearlyTotalDetailAmounts() {

        List<IYearlySumAmounts> yearlySumAmounts = financeStatRepository.findYearlyTotalDetailAmounts();

        Map<Integer, YearlyAggregate> collectByYears = new HashMap<>();

        // big cycle
        yearlySumAmounts.stream().forEach(yearlySumAmount -> {

            YearlyAggregate yearlyAggregate = null;
            if (collectByYears.containsKey(yearlySumAmount.getYear())) {
                yearlyAggregate = collectByYears.get(yearlySumAmount.getYear());
            } else {
                yearlyAggregate = new YearlyAggregate(yearlySumAmount.getYear());
            }

            yearlyAggregate.getBankAmount().put(yearlySumAmount.getName(), yearlySumAmount.getSumma());
            collectByYears.put(yearlySumAmount.getYear(), yearlyAggregate);

        });

        List<IYearlyTotalDetailAmounts> yearTotalDetailAmountsList = new ArrayList<>();
        collectByYears.keySet().stream().forEach(year -> {
            yearTotalDetailAmountsList.add(new YearTotalDetailAmounts(collectByYears.get(year)));
        });

        return yearTotalDetailAmountsList;
    }

    public IHighestValuedBankYear getHighestValuedBankYear() {
        return financeStatRepository.findHighestValuedBankYear();
    }

    public ExtremePointsOfAverage getMinMaxYearlyAveragesOfBank(int id) {
        ArrayList<IYearlyAverage> iYearlyAverages = financeStatRepository.findYearlyAverageByBankId(id);

        if (CollectionUtils.isEmpty(iYearlyAverages)) {
            throw new RuntimeException("No data available for processing!");
        }

        YearlyAverage minYearlyAverage = new YearlyAverage(iYearlyAverages.get(0));
        YearlyAverage maxYearlyAverage = new YearlyAverage(iYearlyAverages.get(0));

        iYearlyAverages.stream().forEach(iYearlyAverage -> {
            if (minYearlyAverage.getAmount() > iYearlyAverage.getAverage()) {
                minYearlyAverage.setYearlyAverage(iYearlyAverage);
            }

            if (maxYearlyAverage.getAmount() < iYearlyAverage.getAverage()) {
                maxYearlyAverage.setYearlyAverage(iYearlyAverage);
            }

        });

        ExtremePointsOfAverage extremePointsOfAverage = new ExtremePointsOfAverage(
                iYearlyAverages.get(0).getName(),
                minYearlyAverage,
                maxYearlyAverage
        );

        return extremePointsOfAverage;

    }

    public FinanceStatPredictResponse getPrediction(int month, String bankName) throws Exception {
        ArimaWrapper arimaWrapper = new ArimaWrapper();

        List<Bank> bankList = bankRepository.findByNameContaining(bankName);

        if (Collections.isEmpty(bankList)) {
            throw new RuntimeException("Bank with such a name not found!: " + bankName);
        }

        int bankId = bankList.get(0).getId();

        List<Integer> amountList = financeStatRepository.findAllAmounts(bankId);
        if (Collections.isEmpty(amountList)) {
            throw new RuntimeException("Amount list cannot be found! for bank: " + bankName);
        }

        double[] doubleAmountArray = new double[amountList.size()];
        for (int i = 0; i < amountList.size(); i++) {
            doubleAmountArray[i] = amountList.get(i);
        }

        int predicted = arimaWrapper.getMonthPrediction(month, doubleAmountArray);

        FinanceStatPredictResponse financeStatPredictResponse = FinanceStatPredictResponse.builder()
                .bank("bnk" + bankId)
                .year(2018)
                .month(month)
                .amount(predicted)
                .build();

        return financeStatPredictResponse;
    }

}
