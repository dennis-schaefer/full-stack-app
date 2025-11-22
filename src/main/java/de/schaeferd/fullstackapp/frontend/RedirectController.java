package de.schaeferd.fullstackapp.frontend;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Profile("dev")
public class RedirectController
{
    @GetMapping("/")
    public RedirectView redirectToViteDevServer()
    {
        // Required to redirect back to frontend after successful login
        return new RedirectView("http://localhost:3000/");
    }
}
