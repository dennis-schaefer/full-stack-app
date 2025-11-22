package de.schaeferd.fullstackapp.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/userinfo")
public class UserInfoController
{
    @GetMapping
    Optional<UserInfo> userInfo(@AuthenticationPrincipal OAuth2User user)
    {
        if (user == null)
            return Optional.empty();

        var roles = user.getAuthorities().stream()
                .map(Object::toString)
                .filter(a -> a.startsWith("ROLE_"))
                .map(a -> a.substring(5))
                .collect(Collectors.toList());

        return Optional.of(new UserInfo(user.getAttribute("preferred_username"),
                user.getAttribute("email"),
                user.getAttribute("name"),
                roles));
    }

    record UserInfo(String username,
                    String email,
                    String fullName,
                    List<String> roles) {}
}
