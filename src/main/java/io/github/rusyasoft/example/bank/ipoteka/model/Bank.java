package io.github.rusyasoft.example.bank.ipoteka.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
public class Bank {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
     
    @Column(nullable = false)
    private String name;


    public Bank(String bankName) {
        this.name = bankName;
    }

}