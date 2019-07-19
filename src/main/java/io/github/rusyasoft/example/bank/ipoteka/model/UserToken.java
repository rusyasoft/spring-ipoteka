package io.github.rusyasoft.example.bank.ipoteka.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
@JsonSerialize
public class UserToken {
    String jwtToken;
}
