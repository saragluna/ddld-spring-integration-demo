package com.azure.spring.integration.todolist.config.aad;

import com.azure.spring.aad.webapp.AADWebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author Xiaolu Dai, 2021/3/14.
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AADOAuth2LoginSecurityConfig extends AADWebSecurityConfigurerAdapter {

    /**
     * Add configuration logic as needed.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);

        // @formatter:off
        http.csrf()
                .disable()
            .authorizeRequests()
                .anyRequest()
                .authenticated();

        // @formatter:on
    }


}
