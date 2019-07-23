package io.github.rusyasoft.example.bank.ipoteka.security.service;

import io.github.rusyasoft.example.bank.ipoteka.security.model.JwtInfoDetails;
import io.github.rusyasoft.example.bank.ipoteka.security.model.LoginParam;
import io.github.rusyasoft.example.bank.ipoteka.security.model.UserEntity;
import io.github.rusyasoft.example.bank.ipoteka.security.model.UserToken;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
public class JwtTokenProviderService {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProviderService.class);

    private static final int ONE_SECOND_IN_MILLISECONDS = 1000;

    @Value("${user-security.jwtSecret}")
    private String jwtSecret;

    @Value("${user-security.jwtExpInSeconds}")
    private int jwtExpInSeconds;

    @Value("${user-security.jwtRefreshExpInSeconds}")
    private int jwtRefreshExpInSeconds;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;


    public JwtInfoDetails authenticateAndGenerateToken(LoginParam loginParam) throws Exception {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpInSeconds * ONE_SECOND_IN_MILLISECONDS);
        return authenticateAndGenerateToken(loginParam.getUsername(), loginParam.getPassword(), now, expiryDate);
    }

    public JwtInfoDetails authenticateAndGenerateToken(String userName, String password, Date issueAt, Date expiryDate) throws Exception {
        String passwordFromDb;
        try {
            passwordFromDb = this.getUserEnityByName(userName).getPassword();
        } catch (Exception e) {
            return failedGenerateToken("Authentication Failed!!!");
        }

        if (!this.comparePassword(password, passwordFromDb)) {
            return failedGenerateToken("Authentication Failed!!!");
        }

        return generateToken(userName, issueAt, expiryDate);
    }

    public JwtInfoDetails generateToken(String userName, Date issueAt, Date expiryDate) {
        try {
            String jwtToken = generateJwtToken(userName, issueAt, expiryDate);

            return JwtInfoDetails.builder()
                    .success(true)
                    .message("Authentication Successful")
                    .token(jwtToken)
                    .build();
        } catch (UnsupportedEncodingException e) {
            log.error("generateToken has thrown an exception: " + e.getMessage());
            return failedGenerateToken("Authentication Failed!!!");
        }
    }

    private String generateJwtToken(String userName, Date issueAt, Date expiryDate) throws UnsupportedEncodingException {
        String jwtToken = Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(issueAt)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes(StandardCharsets.UTF_8))
                .compact();
        return jwtToken;
    }

    private boolean comparePassword(String notEncryptedPassword, String encryptedPassword) {
        //TODO: lets just return true while testing
        return passwordEncoder.matches(notEncryptedPassword, encryptedPassword); // true; // passwordEncoder.matches(notEncryptedPassword, encryptedPassword);
    }

    private UserEntity getUserEnityByName(String name) throws Exception {

        if (StringUtils.isEmpty(name)) {
            throw new RuntimeException("Username must be provided");
        }

        UserEntity userEntity = userService.getUserByName(name);


        return userEntity;
    }


    public JwtInfoDetails refreshToken(UserToken userToken) throws RuntimeException {

        if (Objects.isNull(userToken) || StringUtils.isEmpty(userToken.getJwtToken())) {
            return JwtInfoDetails.builder().success(false).message("Empty Token").token("").build();
        }

        try {

            String jwtToken = userToken.getJwtToken();

            Claims claims = getTokenClaims(jwtToken);

            Long tokenIATseconds = claims.getIssuedAt().getTime() / ONE_SECOND_IN_MILLISECONDS;
            Date now = new Date();
            Long nowInSeconds = now.getTime() / ONE_SECOND_IN_MILLISECONDS;

            // check refresh period match
            if (nowInSeconds >= tokenIATseconds + jwtExpInSeconds + jwtRefreshExpInSeconds) {
                return failedGenerateToken("Refreshing Failed, Authenticate and get new Token !!!");
            }

            // refreshing is allowed, now we generate new token
            String userName = claims.getSubject();
            if (StringUtils.isEmpty(userName)) {
                throw new RuntimeException("Username must be provided in Jwt");
            }

            Date newExpiryDate = new Date(nowInSeconds + jwtExpInSeconds * ONE_SECOND_IN_MILLISECONDS);
            return generateToken(userName, now, newExpiryDate);
        } catch (Exception e) {
            return JwtInfoDetails.builder().success(false).message(e.getMessage()).token("").build();
        }

    }

    private Claims getTokenClaims(String jwtToken) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(jwtToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.info("token expired for id : " + e.getClaims().getId());
            claims = e.getClaims();
        } catch (SignatureException e) {
            throw new RuntimeException("Given token is not valid !");
        }
        return claims;
    }


    private JwtInfoDetails failedGenerateToken(String message) {
        return JwtInfoDetails.builder()
                .success(false)
                .message(message)
                .token("")
                .build();
    }


}