package io.github.rusyasoft.example.bank.ipoteka.security.config;

import io.github.rusyasoft.example.bank.ipoteka.security.filter.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.header.HeaderWriterFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    private BusinessAccessDeniedHandler businessAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/swagger**", "/swagger-resources/**").permitAll()
                .antMatchers("/v2/**", "/h2-console/**", "/security/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers(HttpMethod.GET, "/banks/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/banks/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/input/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                //.apply(new JwtConfigurer(jwtTokenProvider))
                //.and()
                .exceptionHandling().accessDeniedHandler(businessAccessDeniedHandler)
        ;

        http.addFilterBefore(jwtAuthenticationFilter(), HeaderWriterFilter.class);
        //@formatter:on
    }

    @Bean
    public JwtTokenFilter jwtAuthenticationFilter() {
        return new JwtTokenFilter();
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {

        // Ignore static resources and webjars from Spring Security
        web.ignoring()
                .antMatchers("/resources/**")
                .antMatchers("/css/**")
                .antMatchers("/webjars/**")
                .antMatchers("/js/**")
                .antMatchers("/images/**")
        ;

//        // Thymeleaf needs to use the Thymeleaf configured FilterSecurityInterceptor
//        // and not the default Filter from AutoConfiguration.
//        final HttpSecurity http = getHttp();
//        web.postBuildAction(() -> {
//            FilterSecurityInterceptor fsi = http.getSharedObject(FilterSecurityInterceptor.class);
//            fsi.setSecurityMetadataSource(metadataSource);
//            web.securityInterceptor(fsi);
//        });
    }
}