package io.github.rusyasoft.example.bank.ipoteka.business.service;

import com.google.common.collect.Lists;
import io.github.rusyasoft.example.bank.ipoteka.business.model.Bank;
import io.github.rusyasoft.example.bank.ipoteka.business.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankService {


    private BankRepository bankRepository;

    @Autowired
    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public List<String> getBankNameList() {
        return Lists.newArrayList(bankRepository.findAll()).stream().map(Bank::getName).collect(Collectors.toList());
    }
}
