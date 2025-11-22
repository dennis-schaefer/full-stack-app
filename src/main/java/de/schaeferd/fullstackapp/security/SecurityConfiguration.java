package de.schaeferd.fullstackapp.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.server.servlet.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration
{
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)
    {
        http
                .authorizeHttpRequests(request -> request.requestMatchers("/api/v1/userinfo").permitAll())
                .authorizeHttpRequests(request -> request.requestMatchers("/public", "/public/**").permitAll())
                .authorizeHttpRequests(request -> request.requestMatchers("/", "/assets/**").permitAll())
                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
                .oauth2Login(login -> login.defaultSuccessUrl("/", true))   // <-- Important, otherwise redirect will end up at /index.html?continue which causes an error in react app
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .logoutSuccessHandler(oidcLogoutSuccessHandler()))
                .oidcLogout(logout -> logout.backChannel(Customizer.withDefaults()));

        return http.build();
    }

    private LogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler =
                new OidcClientInitiatedLogoutSuccessHandler(this.clientRegistrationRepository);
        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}");

        return oidcLogoutSuccessHandler;
    }

    @Bean
    CookieSameSiteSupplier sameSiteSupplier()
    {
        return CookieSameSiteSupplier.ofLax();
    }
}
