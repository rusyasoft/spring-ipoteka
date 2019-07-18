package io.github.rusyasoft.example.bank.ipoteka.repository;

import io.github.rusyasoft.example.bank.ipoteka.model.Bank;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BankRepository extends CrudRepository<Bank, Long> {
    List<Bank> findByName(String name);
}
