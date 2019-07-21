package io.github.rusyasoft.example.bank.ipoteka.business.service;

import com.google.common.collect.Lists;
import io.github.rusyasoft.example.bank.ipoteka.business.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankService {

    @Autowired
    private BankRepository bankRepository;

    public List<String> getBankNameList() {
        return Lists.newArrayList(bankRepository.findAll()).stream().map(bank-> bank.getName()).collect(Collectors.toList());
    }
}
