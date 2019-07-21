package io.github.rusyasoft.example.bank.ipoteka.security.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
@JsonSerialize
public class UserToken {
    String jwtToken;
}
