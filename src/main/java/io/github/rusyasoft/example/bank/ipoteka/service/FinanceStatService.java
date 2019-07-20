package io.github.rusyasoft.example.bank.ipoteka.service;


import io.github.rusyasoft.example.bank.ipoteka.prediction.ArimaWrapper;
import io.github.rusyasoft.example.bank.ipoteka.model.ExtremePointsOfAverage;
import io.github.rusyasoft.example.bank.ipoteka.model.YearTotalDetailAmounts;
import io.github.rusyasoft.example.bank.ipoteka.model.YearlyAggregate;
import io.github.rusyasoft.example.bank.ipoteka.model.YearlyAverage;
import io.github.rusyasoft.example.bank.ipoteka.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FinanceStatService {

    @Autowired
    public FinanceStatRepository financeStatRepository;

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
        int minAvg = iYearlyAverages.get(0).getAverage();
        int maxAvg = iYearlyAverages.get(0).getAverage();


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

    public int getPrediction(int month) {
        ArimaWrapper arimaWrapper = new ArimaWrapper();

        int bankId = 2;

        List<Integer> amountList = financeStatRepository.findAllAmounts(bankId);

        double [] doubleAmountArray = new double[amountList.size()];
        for (int i = 0; i < amountList.size();i++) {
            doubleAmountArray[i] = amountList.get(i);
        }

        int predicted = arimaWrapper.getMonthPrediction(month, doubleAmountArray);
        return predicted;
    }

}
