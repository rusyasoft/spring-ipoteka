package io.github.rusyasoft.example.bank.ipoteka.service;

import io.github.rusyasoft.example.bank.ipoteka.model.Bank;
import io.github.rusyasoft.example.bank.ipoteka.model.FinanceStat;
import io.github.rusyasoft.example.bank.ipoteka.repository.BankRepository;
import io.github.rusyasoft.example.bank.ipoteka.repository.FinanceStatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class CsvParser {
    private final String CSV_SPLITTER = ",";
    private final String CSV_NEWLINE_SPLITTER = "\n";

    @Autowired
    BankRepository bankRepository;

    @Autowired
    FinanceStatRepository financeStatRepository;

    public boolean parseCsvAndStore(String rawCsvFileString) {

        try {
            List<String> csvLines = splitLines(rawCsvFileString);

            List<Bank> bankList = parseBanks(csvLines.get(0));
            bankRepository.saveAll(bankList);

            int numOfRows = csvLines.size() - 1;

            for (int i = 0; i < numOfRows; i++) {
                List<FinanceStat> financeStatList = parseFinanceStatRow(bankList, csvLines.get(i + 1));
                financeStatRepository.saveAll(financeStatList);
            }
        } catch (Exception e) {
            log.error("Error happened while parsing Csv String, e:" + e.getMessage() );
            return false;
        }

        return true;

    }

    public List<String> splitLines(String rawCsvFileStr) {
        if (StringUtils.isEmpty(rawCsvFileStr)) {
            new RuntimeException("Conversion error: Empty csvFile string");
        }

        String [] parsedString = rawCsvFileStr.split(CSV_NEWLINE_SPLITTER);
        if (parsedString.length <= 0) {
            new RuntimeException("Conversion error: CSV splitting by new lines didn't work");
        }

        return Arrays.asList(parsedString);
    }

    public List<Bank> parseBanks(String rawCsvLine) {
        String [] parsedString = preProcessingCsvRawString(rawCsvLine);

        List<Bank> resultBankList = new ArrayList<>();
        for(int i = 0; i < parsedString.length;i++) {
            String currenBank = parsedString[i];
            boolean flag1 = currenBank.equals("연도");
            boolean flag2 = currenBank.equals("월");
            if (!flag1 && !flag2) {
                resultBankList.add(new Bank(currenBank));
            }
        }

        return resultBankList;
    }

    public List<FinanceStat> parseFinanceStatRow(List<Bank> bankList, String rawCsvLine) {
        String [] parsedString = preProcessingCsvRawString(rawCsvLine);

        // if number of elements less than number of banks, then file format is wrong (2 columns for year and month)
        if (bankList.size() != parsedString.length - 2) {
            new RuntimeException("Conversion error: number of columns for amount not match with bank" + rawCsvLine);
        }

        Integer year = Integer.parseInt(parsedString[0]);
        Integer month = Integer.parseInt(parsedString[1]);

        List<FinanceStat> financeStatList = new ArrayList<>();
        for (int i = 2; i < parsedString.length; i++) {
            FinanceStat financeStat = new FinanceStat(year, month, bankList.get(i - 2).getId(), Integer.parseInt(parsedString[i]));
            financeStatList.add(financeStat);
        }

        return financeStatList;
    }

    private String [] preProcessingCsvRawString(String rawCsvString) {
        if (StringUtils.isEmpty(rawCsvString)) {
            new RuntimeException("CSV pre-processing error: empty raw string is given");
        }

        String [] parsedString = removeQuotationProcessing(rawCsvString).split(CSV_SPLITTER);
        if (parsedString.length <= 0) {
            new RuntimeException("Conversion error: CSV splitting didn't work: " + rawCsvString);
        }

        return parsedString;
    }

    private String removeQuotationProcessing(String rawCsvString) {
        // search for quotation mark and remove all commas inside quotation

        boolean isQuotationOpened = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rawCsvString.length() ;i++) {
            char ch = rawCsvString.charAt(i);

            if (ch == '"') {
                isQuotationOpened = !isQuotationOpened;
                continue;
            }

            if (isQuotationOpened && ch == ',') {
                continue;
            }

            sb.append(ch);

        }

        return sb.toString();
    }

}
