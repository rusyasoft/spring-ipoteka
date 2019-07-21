package io.github.rusyasoft.example.bank.ipoteka.security.controller;

import io.github.rusyasoft.example.bank.ipoteka.security.model.UserEntity;
import io.github.rusyasoft.example.bank.ipoteka.security.model.UserToken;
import io.github.rusyasoft.example.bank.ipoteka.security.model.JwtInfoDetails;
import io.github.rusyasoft.example.bank.ipoteka.security.model.LoginParam;
import io.github.rusyasoft.example.bank.ipoteka.security.service.JwtTokenProviderService;
import io.github.rusyasoft.example.bank.ipoteka.security.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "JwtTokenProvider", description = "JwtTokenProvider Api")
@RestController
@Slf4j
public class JwtTokenProviderController {

    @Autowired
    JwtTokenProviderService jwtTokenProviderService;

    @PostMapping(value = "/jwtAuthentication")
    @ApiOperation(value = "jwt user 받기 (with requestBody)", response = JwtInfoDetails.class)
    public JwtInfoDetails jwtAuthentication(@RequestBody LoginParam loginParam) throws Exception {
        return jwtTokenProviderService.authenticateAndGenerateToken(loginParam);
    }

    @PostMapping(value = "/refreshJwt")
    @ApiOperation(value = "jwt Refresh 하기 (with requestBody)", response = JwtInfoDetails.class)
    public JwtInfoDetails jwtAuthentication(@RequestBody UserToken userToken) {
        return jwtTokenProviderService.refreshToken(userToken);
    }


    @Autowired
    private UserService userService;

    @PostMapping(value = "/addUser")
    public UserEntity addUser(@RequestBody UserEntity userEntity) {
        return userService.addUser(userEntity);
    }



}
