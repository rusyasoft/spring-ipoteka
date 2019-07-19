package io.github.rusyasoft.example.bank.ipoteka.repository;

import io.github.rusyasoft.example.bank.ipoteka.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByName(String name);
}
