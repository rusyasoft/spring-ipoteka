package io.github.rusyasoft.example.bank.ipoteka.security.repository;

import io.github.rusyasoft.example.bank.ipoteka.security.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String name);
}
