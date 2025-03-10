package com.result.view.config;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //https security ka use kareke  , request ko configure kar sakte hai

//        http.authorizeHttpRequests(httpRequest -> {
//            httpRequest.requestMatchers("/admin/add-result").authenticated();
//            httpRequest.requestMatchers("/admin/add-result-action").authenticated();
//            httpRequest.anyRequest().permitAll();
//        });
//


// http.csrf(e -> e.disable());
        http.authorizeHttpRequests(httpRequests -> httpRequests
                        .requestMatchers("/admin/add-result")
                        .authenticated().requestMatchers("/admin/add-result-action")
                        .authenticated().anyRequest()
                        .permitAll()
                )
                .formLogin(formLogin ->
                        formLogin.loginPage("/user-login")
                                .loginProcessingUrl("/do-login")
                                .successForwardUrl("/admin/result-page")
                                .permitAll()

                ).logout(logout ->
                        logout.logoutUrl("/user-logout")
                                .logoutSuccessUrl("/user-login?logout")
                                .permitAll());


//object mil jaye security filter chain
        DefaultSecurityFilterChain build = http.build();
        return build;
    }
}
