package io.github.rusyasoft.example.bank.ipoteka.security.controller;

import io.github.rusyasoft.example.bank.ipoteka.security.model.*;
import io.github.rusyasoft.example.bank.ipoteka.security.service.JwtTokenProviderService;
import io.github.rusyasoft.example.bank.ipoteka.security.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "JwtTokenProvider", description = "토큰과 사용자 관리")
@RequestMapping("/security/")
@RestController
@Slf4j
public class JwtTokenProviderController {

    @Autowired
    JwtTokenProviderService jwtTokenProviderService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/jwtAuthentication")
    @ApiOperation(value = "로그인하고 jwt 토큰을 받기", response = JwtInfoDetails.class)
    public JwtInfoDetails jwtAuthentication(@RequestBody LoginParam loginParam) throws Exception {
        return jwtTokenProviderService.authenticateAndGenerateToken(loginParam);
    }

    @PostMapping(value = "/refreshJwt")
    @ApiOperation(value = "jwt 토큰 Refresh 하기", response = JwtInfoDetails.class)
    public JwtInfoDetails jwtAuthentication(@RequestBody UserToken userToken) {
        return jwtTokenProviderService.refreshToken(userToken);
    }

    @PostMapping(value = "/addUser")
    @ApiOperation(value = "사용자 추가 하기 (with requestBody)", response = UserEntityResponse.class)
    public UserEntityResponse addUser(@RequestBody UserEntity userEntity) {
        UserEntityResponse userEntityResponse = new UserEntityResponse(userService.addUser(userEntity));
        return userEntityResponse;
    }
}
