package de.schaeferd.fullstackapp.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/userinfo")
public class UserInfoController
{
    @GetMapping
    Optional<UserInfo> userInfo(@AuthenticationPrincipal OAuth2User user)
    {
        if (user == null)
            return Optional.empty();

        return Optional.of(new UserInfo(user.getAttribute("preferred_username"),
                user.getAttribute("email"),
                user.getAttribute("name"),
                user.getAuthorities().stream().map(Object::toString).toList()));
    }

    record UserInfo(String username,
                    String email,
                    String fullName,
                    List<String> roles) {}
}
