package io.github.rusyasoft.example.bank.ipoteka.file.service;



import io.github.rusyasoft.example.bank.ipoteka.business.model.Bank;
import io.github.rusyasoft.example.bank.ipoteka.business.model.FinanceStat;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
public class CsvParserTest {

    private final String INPUT_STRING = "연도,월,주택도시기금1)(억원),국민은행(억원),우리은행(억원),신한은행(억원),한국시티은행(억원),하나은행(억원),농협은행/수협은행(억원),외환은행(억원),기타은행(억원)\n" +
            "2005,1,1019,846,82,95,30,157,57,80,99\n" +
            "2005,2,1144,864,91,97,35,168,36,111,114\n" +
            "2005,3,1828,1234,162,249,54,260,112,171,149\n" +
            "2005,4,2246,1176,209,167,66,291,101,220,111";
    private final int INPUT_STRING_LINE_NUM = 5;
    private final int BANK_NUMBERS = 9;
    private final String QUOTED_INPUT_STRING = "2016,1,\"5,961\",\"4,370\",\"3,802\",\"1,819\",3,\"2,133\",\"2,565\",325,\"5,379\"";
    private final String QUOTED_OUTPUT_STRING = "2016,1,5961,4370,3802,1819,3,2133,2565,325,5379";

    @Autowired
    CsvParser csvParser;

    @Test
    public void parseCsvAndStoreTest() {
        boolean testResult = csvParser.parseCsvAndStore(INPUT_STRING);
        Assert.assertTrue (testResult);
    }

    @Test
    public void splitLinesTest() {
        List<String> dividedLines = csvParser.splitLines(INPUT_STRING);
        Assert.assertEquals(INPUT_STRING_LINE_NUM, dividedLines.size());
    }

    @Test
    public void parseBanksTest() {
        List<String> dividedLines = csvParser.splitLines(INPUT_STRING);
        List<Bank> bankList = csvParser.parseBanks(dividedLines.get(0));
        Assert.assertEquals(BANK_NUMBERS, bankList.size());
    }

    @Test
    public void parseFinanceStatRowTest() {
        List<String> dividedLines = csvParser.splitLines(INPUT_STRING);
        List<Bank> bankList = csvParser.parseBanks(dividedLines.get(0));

        int numOfRows = dividedLines.size();
        for (int i = 1; i < numOfRows ;i++) {
            List<FinanceStat> financeStatList = csvParser.parseFinanceStatRow(bankList, dividedLines.get(i));
            Assert.assertEquals(BANK_NUMBERS, financeStatList.size());
        }
    }

    @Test
    public void preProcessingCsvRawStringTest() {
        String [] resultStringArray = csvParser.preProcessingCsvRawString(QUOTED_INPUT_STRING);

        Assert.assertEquals(BANK_NUMBERS+2, resultStringArray.length);
    }

    @Test
    public void removeQuotationProcessingTest() {
        String resultString = csvParser.removeQuotationProcessing(QUOTED_INPUT_STRING);
        Assert.assertEquals(QUOTED_OUTPUT_STRING, resultString);
    }

}
