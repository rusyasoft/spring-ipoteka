package io.github.rusyasoft.example.bank.ipoteka.business.model;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class FinanceStat {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer month;
     
    @Column(nullable = false)
    private Integer bankId;

    @Column(nullable = false)
    private Integer amount;

    public FinanceStat(Integer year, Integer month, Integer bankId, Integer amount) {
        this.year = year;
        this.month = month;
        this.bankId = bankId;
        this.amount = amount;
    }
}