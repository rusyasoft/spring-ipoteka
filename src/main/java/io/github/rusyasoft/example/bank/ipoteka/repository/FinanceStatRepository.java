package io.github.rusyasoft.example.bank.ipoteka.repository;

import io.github.rusyasoft.example.bank.ipoteka.model.FinanceStat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FinanceStatRepository extends CrudRepository<FinanceStat, Long> {
//    List<FinanceStat> findByBankId();
}
