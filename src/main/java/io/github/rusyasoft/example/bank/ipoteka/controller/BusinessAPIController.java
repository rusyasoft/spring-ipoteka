package io.github.rusyasoft.example.bank.ipoteka.controller;

import com.google.common.collect.Lists;
import io.github.rusyasoft.example.bank.ipoteka.model.Bank;
import io.github.rusyasoft.example.bank.ipoteka.repository.BankRepository;
import io.github.rusyasoft.example.bank.ipoteka.service.CsvParser;
import io.github.rusyasoft.example.bank.ipoteka.service.CsvFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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

        return csvFileReader.readResourceFile("123.csv");
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
}
