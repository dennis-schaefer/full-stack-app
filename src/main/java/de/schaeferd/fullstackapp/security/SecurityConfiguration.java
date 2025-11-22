package de.schaeferd.fullstackapp.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.server.servlet.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration
{
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)
    {
        http
                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
                .oauth2Login(login -> login
                        .loginPage("/oauth2/authorization/keycloak")
                        .defaultSuccessUrl("/")
                );

        return http.build();
    }

    @Bean
    CookieSameSiteSupplier sameSiteSupplier()
    {
        return CookieSameSiteSupplier.ofLax();
    }
}
