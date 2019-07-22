package io.github.rusyasoft.example.bank.ipoteka.business.controller;

import io.github.rusyasoft.example.bank.ipoteka.business.model.ExtremePointsOfAverage;
import io.github.rusyasoft.example.bank.ipoteka.business.model.FinanceStatPredictResponse;
import io.github.rusyasoft.example.bank.ipoteka.business.model.IHighestValuedBankYear;
import io.github.rusyasoft.example.bank.ipoteka.business.model.IYearlyTotalDetailAmounts;
import io.github.rusyasoft.example.bank.ipoteka.business.service.BankService;
import io.github.rusyasoft.example.bank.ipoteka.business.service.FinanceStatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "Business", description = "주택 금융 서비스 API")
@RequestMapping("/banks/")
@RestController
public class BusinessApiController {


//    //TODO: should be removed
//    @GetMapping("/test")
//    public String test() {
//        ArimaWrapperUsage arimaWrapperUsage = new ArimaWrapperUsage();
//        return "test";
//    }

    @Autowired
    private BankService bankService;

    @Autowired
    private FinanceStatService financeStatService;

    @GetMapping("/")
    @ApiOperation(value = "주택금융 공급 금융기관(은행) 목록을 출력하는 API", response = List.class)
    public List<String> getBankNameList() {
        return bankService.getBankNameList();
    }

    @GetMapping("/yearlyTotalDetail")
    @ApiOperation(value = "년도별 각 금융기관의 지원금액 합계를 출력하는 API", response = List.class)
    public List<IYearlyTotalDetailAmounts> getYearlyTotalDetails() {
        return financeStatService.getYearlyTotalDetailAmounts();
    }

    @GetMapping("/highestValuedBankYear")
    @ApiOperation(value = "각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명을 출력하는 API", response = IHighestValuedBankYear.class)
    public IHighestValuedBankYear getHighestValuedBankYear() {
        return financeStatService.getHighestValuedBankYear();
    }

    @GetMapping("/extremeAveragePoints")
    @ApiOperation(value = "전체 년도(2005~2016)에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰\n" +
            "금액을 출력하는 API", response = ExtremePointsOfAverage.class)
    public ExtremePointsOfAverage getExtremePointsOfAverage() {
        // here 8 is KEB bank which is statically given as it is in spec
        return financeStatService.getMinMaxYearlyAveragesOfBank(8);
    }

    @GetMapping("/predict")
    @ApiOperation(value = "특정 은행의 특정 달에 대해서 2018 년도 해당 달에 금융지원 금액을 예측하는 API", response = FinanceStatPredictResponse.class)
    public FinanceStatPredictResponse getPrediction(int month, String bankName) throws Exception {
        return financeStatService.getPrediction(month, bankName);
    }
}
