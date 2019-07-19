package io.github.rusyasoft.example.bank.ipoteka.model;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@JsonSerialize
@Data
public class LoginParam {
    private String loginId;
    private String password;
}
