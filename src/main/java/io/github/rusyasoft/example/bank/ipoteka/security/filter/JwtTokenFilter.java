package io.github.rusyasoft.example.bank.ipoteka.security.filter;

import io.github.rusyasoft.example.bank.ipoteka.security.config.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {


    @Autowired
    private JwtTokenProvider jwtTokenProvider;

//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
//            throws IOException, ServletException {
//        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
//        if (token != null && jwtTokenProvider.validateToken(token)) {
//            Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
//            SecurityContextHolder.getContext().setAuthentication(auth);
//        }
//        filterChain.doFilter(req, res);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (Exception e) {
            response.sendError( HttpStatus.UNAUTHORIZED.value(), "Access Denied: " + e.getMessage());
        }

        filterChain.doFilter(request, response);

    }
}