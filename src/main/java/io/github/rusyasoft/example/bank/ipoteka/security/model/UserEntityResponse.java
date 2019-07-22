package io.github.rusyasoft.example.bank.ipoteka.security.model;

import lombok.Data;

import javax.persistence.Entity;

@Data
public class UserEntityResponse {
    private Long id;
    private String username;
    private String email;

    public UserEntityResponse(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.email = userEntity.getEmail();
        this.username = userEntity.getUsername();
    }
}
