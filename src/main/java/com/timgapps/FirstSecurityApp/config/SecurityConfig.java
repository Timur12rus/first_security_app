package com.timgapps.FirstSecurityApp.config;

import com.timgapps.FirstSecurityApp.security.AuthProviderImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// Этот класс является главным классом, где мы настраиваем SpringSecurity
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthProviderImpl authProvider;

    public SecurityConfig(AuthProviderImpl authProvider) {
        this.authProvider = authProvider;
    }

    // должны указать логику аутентификации
    // в этом методе мы уже сконфигурируем нашу аутентификацию
    // настраивает аутентификацию
    // даем понять springSecurity, что мы используем этот authentication provider для аутентификации пользователей
    // данные с формы будут переданы в AuthProvierImpl в метод authenticate, в котором описана наша логика
    // аутентификации пользователя.
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }
}
