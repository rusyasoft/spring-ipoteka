package io.github.rusyasoft.example.bank.ipoteka.business.repository;

import io.github.rusyasoft.example.bank.ipoteka.business.model.FinanceStat;
import io.github.rusyasoft.example.bank.ipoteka.business.model.IHighestValuedBankYear;
import io.github.rusyasoft.example.bank.ipoteka.business.model.IYearlyAverage;
import io.github.rusyasoft.example.bank.ipoteka.business.model.IYearlySumAmounts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface FinanceStatRepository extends CrudRepository<FinanceStat, Long> {
//    List<FinanceStat> findByBankId();

    //@Query(value = "select id,name,roll_no from USER_INFO_TEST where rollNo = ?1", nativeQuery = true)
    @Query(value = "select year, name as bank from (\n" +
            "   select sums.year, sums.bank_id, sums.summa \n" +
            "   from (select year, bank_id as bank_id, sum(amount) as summa from finance_stat group by year, bank_id order by year, bank_id) sums \n" +
            "   where sums.summa = (select max(sums.summa) as max_summa  \n" +
            "from (select year, bank_id as bank_id, sum(amount) as summa from finance_stat group by year, bank_id order by year, bank_id) sums) \n" +
            ") max_summa_bank inner join bank on max_summa_bank.bank_id = bank.id"
    , nativeQuery = true)
    IHighestValuedBankYear findHighestValuedBankYear();



    @Query(value = "select year, name, summa from bank inner join (select year, bank_id, sum(amount) as summa from finance_stat group by year, bank_id order by year, bank_id) as ft on bank.id = ft.bank_id"
            ,nativeQuery = true)
    ArrayList<IYearlySumAmounts> findYearlyTotalDetailAmounts();


    @Query(value = "select year, name, average \n" +
            "from bank inner join (select year, bank_id, avg(amount) as average \n" +
            "from finance_stat  where bank_id = ?1 group by year order by year) as ft \n" +
            "on bank.id = ft.bank_id", nativeQuery = true)
    ArrayList<IYearlyAverage> findYearlyAverageByBankId(int bankid);

    @Query(value = "SELECT amount FROM FINANCE_STAT where bank_id = ?1", nativeQuery = true)
    ArrayList<Integer> findAllAmounts(int bankid);
}
