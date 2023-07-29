package com.timgapps.FirstSecurityApp.config;

import com.timgapps.FirstSecurityApp.services.PersonDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    // даем понять springSecurity, что мы используем этот authentication provider для аутентификации пользователей
    // данные с формы будут переданы в AuthProvierImpl в метод authenticate, в котором описана наша логика
    // аутентификации пользователя.

    // настраиваем аутентификацию в этом методе
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder());  // чтобы использовать алгоритм BCrypt просто добавим это сюда
        //  теперь springSecurity автоматически будет прогонять пароль из формы через этот BCryptPasswordEncoder при аутентификации
        // и два зашифрованных пароля будут сравниваться и spring будет либо пускать либо не пускать пользователя
    }

    @Override
    // это два разных метода, переопределяем метод родителя, они принимают разные параметры на вход
    // конфигурируем сам Spring Security
    // страницам, на основании его статуса(обычный пользователь, администратор, аутонтифицированный или нет и т.д.
    // указываем какую форму для логина использовать
    protected void configure(HttpSecurity http) throws Exception {

        // -----------------конфигурируем авторизацию,---------------------
        http.csrf().disable()  // отключаем защиту от межсайтовой подделки запросов

                // далее будем давать или не давать доступ пользователю к определенным
                // всё что ниже назвается "правила авторизации", они читаются сверху вниз, сначала идут более специфичные,
                // а далее более общие правила
                .authorizeRequests()
                // если вызываем этот метод, то все запросы которые приходят к нам в приложение
                // будут проходить через нашу авторизацию, которую мы здесь настроим
                .antMatchers("/auth/login", "/error", "/auth/registration").permitAll() // используем antMatcher'ы чтобы смотреть какой запрос пришел
                // к нам в приложение, и если пришел такой запрос, мы должны его пускать на страницу с адресом
                // "/auth/login" и пускаем любого пользователя на страницу "/error", они должны быть доступны всем,
                // их доступность мы указываем с помощью antMatcher'а
                // permitAll() - допускает, независимо от его роли, прошел аутентификацию или нет

                // на все остальные запросы мы вызываем метод authenticated, т.е. указываем, что пользователь должен
                // быть аутентифицирован
                .anyRequest().authenticated()
                .and()      // вызываем and(), чтобы перейти от настройки авторизации к настройке страницы логина

                // --------- настраиваем форму для входа-------------------
                // передаем адрес с формой для входа
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")  // просто указываем, данные обработает за нас SpringSecurity
                // в представлении полей названия должны быть именно: name="username", password="password", именно такие поля
                // ждет spring security
                .defaultSuccessUrl("/hello", true) // в случае успешной аутентификации, чтобы
                // перенаправлял на адрес /hello и указать true
                .failureUrl("/auth/login?error"); // в случае не успешной авторизации
        // указываем на какую страницу перейти
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
