package io.github.rusyasoft.example.bank.ipoteka.business.repository;

import io.github.rusyasoft.example.bank.ipoteka.business.model.Bank;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BankRepository extends CrudRepository<Bank, Integer> {
    List<Bank> findByName(String name);

    List<Bank> findByNameContaining(String name);

}
