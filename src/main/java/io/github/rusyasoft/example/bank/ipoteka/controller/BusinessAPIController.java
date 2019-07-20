package io.github.rusyasoft.example.bank.ipoteka.controller;

import com.google.common.collect.Lists;
import io.github.rusyasoft.example.bank.ipoteka.prediction.ArimaWrapperUsage;
import io.github.rusyasoft.example.bank.ipoteka.model.Bank;
import io.github.rusyasoft.example.bank.ipoteka.model.ExtremePointsOfAverage;
import io.github.rusyasoft.example.bank.ipoteka.repository.BankRepository;
import io.github.rusyasoft.example.bank.ipoteka.repository.IHighestValuedBankYear;
import io.github.rusyasoft.example.bank.ipoteka.repository.IYearlyTotalDetailAmounts;
import io.github.rusyasoft.example.bank.ipoteka.service.CsvParser;
import io.github.rusyasoft.example.bank.ipoteka.service.CsvFileReader;
import io.github.rusyasoft.example.bank.ipoteka.service.FinanceStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BusinessAPIController {

    @Autowired
    CsvFileReader csvFileReader;

    @Autowired
    CsvParser csvParser;



    @GetMapping("/test")
    public String test() {
        ArimaWrapperUsage arimaWrapperUsage = new ArimaWrapperUsage();
        return "test";
        //return csvFileReader.readResourceFile("123.csv");
    }

    @GetMapping("/parse")
    public Boolean parseThem() {
        String totalStr = csvFileReader.readResourceFile("123.csv");

        return csvParser.parseCsvAndStore(totalStr);
    }

    @Autowired
    private BankRepository bankRepository;

    @GetMapping("/banks")
    public List<Bank> getBankNameList() {

//        return bankRepository.findById(0).get();
        return Lists.newArrayList(bankRepository.findAll());
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
    public int getPrediction(int month) {
        return financeStatService.getPrediction(month);
    }
}
