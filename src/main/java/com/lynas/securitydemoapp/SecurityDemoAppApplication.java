package com.lynas.securitydemoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@EnableOAuth2Sso
@SpringBootApplication
@RestController
public class SecurityDemoAppApplication extends WebSecurityConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(SecurityDemoAppApplication.class, args);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**", "/webjars/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        ;
    }

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }
}
