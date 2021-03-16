package com.azure.spring.integration.todolist.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Xiaolu Dai, 2021/3/16.
 */
@Controller
public class HomeController {

    @RequestMapping("/home")
    public Map<String, Object> home(@AuthenticationPrincipal OidcUser principal) {
        final Map<String, Object> model = new HashMap<>();
        model.put("id", principal.getName());
        model.put("content", "home");
        return model;
    }
}
