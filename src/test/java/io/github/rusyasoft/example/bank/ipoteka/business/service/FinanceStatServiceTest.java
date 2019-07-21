package io.github.rusyasoft.example.bank.ipoteka.business.service;

import io.github.rusyasoft.example.bank.ipoteka.business.model.IYearlySumAmounts;
import io.github.rusyasoft.example.bank.ipoteka.business.model.IYearlyTotalDetailAmounts;
import io.github.rusyasoft.example.bank.ipoteka.business.repository.FinanceStatRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FinanceStatServiceTest {

    @Autowired
    private FinanceStatService financeStatService;

    @MockBean
    private FinanceStatRepository financeStatRepository;

    @Before
    public void beforeLoad() {

        when(financeStatRepository.findYearlyTotalDetailAmounts())
                .thenReturn(getListOfIYearlyTotalDetailAmounts());

    }

    int globalIndex = 0;
    private ArrayList<IYearlySumAmounts> getListOfIYearlyTotalDetailAmounts() {

        ArrayList<IYearlySumAmounts> iYearlySumAmountsList = new ArrayList<>();

        for (globalIndex = 1; globalIndex <= 5; globalIndex++) {
            YearlySumAmountsTest iYearlySumAmounts = new YearlySumAmountsTest();
            iYearlySumAmounts.setYear( 2016);
            iYearlySumAmounts.setName("bank"+globalIndex);
            iYearlySumAmounts.setSumma(globalIndex * 100);
            iYearlySumAmountsList.add(iYearlySumAmounts);
        }

        return iYearlySumAmountsList;
    }

    @Test
    public void getYearlyTotalDetailAmountsTest() {
        List<IYearlyTotalDetailAmounts> yearlyTotalDetail = financeStatService.getYearlyTotalDetailAmounts();

        Assert.assertEquals(1500, yearlyTotalDetail.get(0).getTotal_amount() );
    }
}
