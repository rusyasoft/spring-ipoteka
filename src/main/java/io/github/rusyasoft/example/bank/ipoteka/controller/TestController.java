package io.github.rusyasoft.example.bank.ipoteka.controller;

import io.github.rusyasoft.example.bank.ipoteka.service.CsvReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    CsvReader csvReader;

    @GetMapping("/test")
    public String test() {

        return csvReader.readFile();
    }
}
