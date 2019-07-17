package io.github.rusyasoft.example.bank.ipoteka.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Bank {
 
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
     
    @Column(nullable = false)
    private String name;

}