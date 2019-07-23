package io.github.rusyasoft.example.bank.ipoteka.security.model;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@JsonSerialize
@Data
public class LoginParam {
    private String username;
    private String password;
}
