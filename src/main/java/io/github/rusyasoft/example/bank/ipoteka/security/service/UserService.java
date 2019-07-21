package io.github.rusyasoft.example.bank.ipoteka.security.service;

import com.google.common.collect.Lists;
import io.github.rusyasoft.example.bank.ipoteka.security.model.UserEntity;
import io.github.rusyasoft.example.bank.ipoteka.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserEntity addUser(UserEntity admin) {
        return userRepository.save(encryptUserPassword(admin));
    }

    public UserEntity updateUser(UserEntity admin) {
        return userRepository.save(encryptUserPassword(admin));
    }

    public UserEntity resetPassword(Long adminId, String rawPassword) {
        UserEntity admin = this.getUserById(adminId);
        if (admin !=  null) {
            admin.setPassword(rawPassword);
            updateUser(admin);
        }
        return admin;
    }

    private UserEntity encryptUserPassword(UserEntity userEntity) {
        if (userEntity !=  null) {
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        }
        return userEntity;
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElseGet(null);
    }

    public List<UserEntity> getAllUsers() {
        return Lists.newArrayList(userRepository.findAll());
    }

    public UserEntity getUserByName(String name) throws Exception {
         return userRepository.findByUsername(name).orElseThrow(() -> new UsernameNotFoundException("Username: " + name + " not found"));
    }
}
