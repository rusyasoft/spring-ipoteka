package io.github.rusyasoft.example.bank.ipoteka.business.controller;

import io.github.rusyasoft.example.bank.ipoteka.business.model.FinanceStatPredictResponse;
import io.github.rusyasoft.example.bank.ipoteka.business.service.BankService;
import io.github.rusyasoft.example.bank.ipoteka.prediction.ArimaWrapperUsage;
import io.github.rusyasoft.example.bank.ipoteka.business.model.ExtremePointsOfAverage;
import io.github.rusyasoft.example.bank.ipoteka.business.model.IHighestValuedBankYear;
import io.github.rusyasoft.example.bank.ipoteka.business.model.IYearlyTotalDetailAmounts;
import io.github.rusyasoft.example.bank.ipoteka.business.service.FinanceStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BusinessAPIController {


    //TODO: should be removed
    @GetMapping("/test")
    public String test() {
        ArimaWrapperUsage arimaWrapperUsage = new ArimaWrapperUsage();
        return "test";
    }


    @Autowired
    private BankService bankService;

    @GetMapping("/banks")
    public List<String> getBankNameList() {
        return bankService.getBankNameList();
    }


    @Autowired
    private FinanceStatService financeStatService;

    @GetMapping("/banks/yearlyTotalDetail")
    public List<IYearlyTotalDetailAmounts> getYearlyTotalDetails() {
        return financeStatService.getYearlyTotalDetailAmounts();
    }

    @GetMapping("/banks/highestValuedBankYear")
    public IHighestValuedBankYear getHighestValuedBankYear() {
        return financeStatService.getHighestValuedBankYear();
    }

    @GetMapping("/banks/extremeAveragePoints")
    public ExtremePointsOfAverage getExtremePointsOfAverage() {
        // here 8 is KEB bank which is statically given as it is in spec
        return financeStatService.getMinMaxYearlyAveragesOfBank(8);
    }

    @GetMapping("/banks/predict")
    public FinanceStatPredictResponse getPrediction(int month, String bankName) throws Exception {
        return financeStatService.getPrediction(month, bankName);
    }
}
