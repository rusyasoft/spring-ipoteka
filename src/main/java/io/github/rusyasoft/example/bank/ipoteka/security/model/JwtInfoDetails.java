package io.github.rusyasoft.example.bank.ipoteka.security.model;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;

@Builder
@JsonSerialize
public class JwtInfoDetails {
    public boolean success;
    public String message;
    public String token;
}