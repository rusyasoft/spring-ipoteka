package io.github.rusyasoft.example.bank.ipoteka.security.config;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class BusinessAccessDeniedHandler implements AccessDeniedHandler {
 
    @Override
    public void handle
      (HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
      throws IOException, ServletException {
        response.sendError(HttpStatus.UNAUTHORIZED.value());
    }
}