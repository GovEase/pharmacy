package com.ouharri.pharma.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;

@Configuration
@RequiredArgsConstructor
public class SecurityBean {

    /**
     * Creates and configures the CSRF token repository.
     * This repository is responsible for storing and managing CSRF tokens,
     * using cookies as the storage mechanism.
     *
     * @return A CsrfTokenRepository object for managing CSRF tokens.
     */
    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        CookieCsrfTokenRepository repository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        repository.setCookiePath("/");
        return repository;
    }
}
