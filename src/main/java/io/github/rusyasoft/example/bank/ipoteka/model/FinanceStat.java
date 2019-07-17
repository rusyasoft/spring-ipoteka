package io.github.rusyasoft.example.bank.ipoteka.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class FinanceStat {
 
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer month;
     
    @Column(nullable = false)
    private Integer bankId;

    @Column(nullable = false)
    private Integer amount;
}