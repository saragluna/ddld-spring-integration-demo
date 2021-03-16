package com.azure.spring.integration.todolist.controller;

import com.azure.spring.integration.todolist.dao.UserRepository;
import com.azure.spring.integration.todolist.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Xiaolu Dai, 2021/3/14.
 */
@RestController
public class UsersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("me")
    public User getCurrentUser(@AuthenticationPrincipal OidcUser principal) {
        User user = userRepository.findByEmail(principal.getEmail());

        if (user == null) {
            user = new User(UUID.randomUUID().toString(),
                principal.getGivenName(),
                principal.getFamilyName(),
                principal.getEmail());
            LOGGER.info("Saving the user: {}", user);
            user = userRepository.save(user);
        }

        return user;
    }

}
