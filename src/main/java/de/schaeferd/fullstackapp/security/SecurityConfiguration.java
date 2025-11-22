package de.schaeferd.fullstackapp.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.server.servlet.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.nimbusds.jwt.SignedJWT;

import java.util.*;

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
                .oauth2Login(login -> login
                        .defaultSuccessUrl("/", true)
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(oidcUserService())))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .logoutSuccessHandler(oidcLogoutSuccessHandler()))
                .oidcLogout(logout -> logout.backChannel(Customizer.withDefaults()));

        return http.build();
    }

    /**
     * Custom OidcUserService: Liest Rollen aus Access Token (realm_access.roles, resource_access.<clientId>.roles)
     * und ergänzt sie als ROLE_ Authorities.
     */
    @Bean
    OidcUserService oidcUserService()
    {
        var delegate = new OidcUserService();
        return new OidcUserService()
        {
            @Override
            public OidcUser loadUser(OidcUserRequest userRequest)
            {
                var oidcUser = delegate.loadUser(userRequest);
                Set<GrantedAuthority> authorities = new HashSet<>();

                var accessToken = userRequest.getAccessToken();

                if (accessToken != null)
                {
                    var tokenValue = accessToken.getTokenValue();

                    try
                    {
                        var signedJWT = SignedJWT.parse(tokenValue);
                        var claimsSet = signedJWT.getJWTClaimsSet();
                        var claims = claimsSet.getClaims();
                        log.debug("Access Token Claim Keys: {}", claims.keySet());

                        // realm_access.roles
                        var realmAccessObj = claims.get("realm_access");
                        if (realmAccessObj instanceof Map<?,?> realmMap)
                        {
                            Object rolesObj = realmMap.get("roles");
                            addRoles(rolesObj, authorities);
                        }
                    }
                    catch (Exception e)
                    {
                        log.warn("Failed to parse Access Token: {}", e.getMessage());
                    }
                }

                var nameAttrKey = oidcUser.getAttribute("preferred_username") != null ? "preferred_username" : "sub";
                return new DefaultOidcUser(authorities, userRequest.getIdToken(), oidcUser.getUserInfo(), nameAttrKey);
            }
        };
    }

    private void addRoles(Object rolesObj, Set<GrantedAuthority> target)
    {
        if (rolesObj instanceof Collection<?> roles)
        {
            for (Object roleObj : roles)
            {
                if (roleObj instanceof String role && !role.isBlank())
                    target.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
        }
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
