package com.timgapps.FirstSecurityApp.config;

import com.timgapps.FirstSecurityApp.services.PersonDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Этот класс является главным классом, где мы настраиваем SpringSecurity
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final PersonDetailsService personDetailsService;

    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;

    }

    // должны указать логику аутентификации
    // в этом методе мы уже сконфигурируем нашу аутентификацию
    // настраивает аутентификацию
    // даем понять springSecurity, что мы используем этот authentication provider для аутентификации пользователей
    // данные с формы будут переданы в AuthProvierImpl в метод authenticate, в котором описана наша логика
    // аутентификации пользователя.
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
