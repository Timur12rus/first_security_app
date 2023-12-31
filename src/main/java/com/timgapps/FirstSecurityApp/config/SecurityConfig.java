package com.timgapps.FirstSecurityApp.config;

import com.timgapps.FirstSecurityApp.services.PersonDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Этот класс является главным классом, где мы настраиваем SpringSecurity
@EnableWebSecurity
// включим возможность авотризации на уровне методов
// для этого используем аннотацию
@EnableGlobalMethodSecurity(prePostEnabled = true)
// нужно где-то в коде повесить аннотацию @PreAuthorize, теперь мы можем её использовать
// и теперь SringSecurity будет проверять, что у пользователя есть какая-то роль, которую мы укажем, прежде чем выполянть метод, который мы укажем
// эту аннотацию не используют в контроллерах
// например эту аннотацию вешают в сервисах, чтобы блокировать доступ на уровне сервисов
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
                // тот пароль, который человек ввел в форму при аутентификации, нужно прогнать через BCrypt
                // чтобы сравнивать два зашифрованных пароля
                // чтобы использовать алгоритм BCrypt при аутентификации
                // вызываем метод .passwordEncoder()
                .passwordEncoder(getPasswordEncoder());
        // теперь Spring Security автоматически при аутентификации будет прогонять через алгоритм шифрования паролей
        // BCrypt прогонять пароль из формы, соответсвенно два этих пароля будут сравниваться и Spring будет либо
        // пускать человека либо не пускать
    }

    @Override
    // это два разных метода, переопределяем метод родителя, они принимают разные параметры на вход
    // конфигурируем сам Spring Security
    // страницам, на основании его статуса(обычный пользователь, администратор, аутонтифицированный или нет и т.д.
    // указываем какую форму для логина использовать
    protected void configure(HttpSecurity http) throws Exception {

        // -----------------конфигурируем авторизацию,---------------------
        http
                // .csrf().disable()  // отключаем защиту от межсайтовой подделки запросов

                // далее будем давать или не давать доступ пользователю к определенным
                // всё что ниже назвается "правила авторизации", они читаются сверху вниз, сначала идут более специфичные,
                // а далее более общие правила
                .authorizeRequests()
                // если вызываем этот метод, то все запросы которые приходят к нам в приложение
                // будут проходить через нашу авторизацию, которую мы здесь настроим
                // для админа настраиваем доступ к адресу
//                .antMatchers("/admin").hasRole("ADMIN") // здесь не указываем слово "ROLE_"
                // spring security понимает автоматически, что у нас роль "ROLE_ADMIN"
                ////****************************************
                .antMatchers("/auth/login", "/error", "/auth/registration").permitAll() // используем antMatcher'ы чтобы смотреть какой запрос пришел
                // здесь мы хотим чтобы ко всем остальным страницам("/hello" и "/showUserInfo") получали доступ
                // как обычные пользователи, так и администратор (т.е. даем доступу user'у и администратору)
                .anyRequest().hasAnyRole("USER", "ADMIN")
                // к нам в приложение, и если пришел такой запрос, мы должны его пускать на страницу с адресом
                // "/auth/login" и пускаем любого пользователя на страницу "/error", они должны быть доступны всем,
                // их доступность мы указываем с помощью antMatcher'а
                // permitAll() - допускает, независимо от его роли, прошел аутентификацию или нет

                // на все остальные запросы мы вызываем метод authenticated, т.е. указываем, что пользователь должен
                // быть аутентифицирован
//                .anyRequest().authenticated() // настраиваем авторизацию и убираем anyRequest и разделяем доступ к
                // страницам нашего приложения
                .and()      // вызываем and(), чтобы перейти от настройки авторизации к настройке страницы логина

                // --------- настраиваем форму для входа-------------------
                // передаем адрес с формой для входа
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")  // просто указываем, данные обработает за нас SpringSecurity
                // в представлении полей названия должны быть именно: name="username", password="password", именно такие поля
                // ждет spring security
                .defaultSuccessUrl("/hello", true) // в случае успешной аутентификации, чтобы
                // перенаправлял на адрес /hello и указать true
                .failureUrl("/auth/login?error") // в случае не успешной авторизации

                .and()
                // настроим логаут(выход)
                // логаут - это когда из сессии удаляется пользователь и у пользователя в браузере удаляются кукис
                // spring сам сделает разлогинирование, нам нужно только указать адрес
                .logout()
                .logoutUrl("/logout") //логаут это когда из сессии удаляется пользователь, и когда в
                //браузере удаляются кукис
                // мы сами не реализуем логику логаута, за нас это делает spring security,
                // spring security сам удалит сессию и удалит кукис пользователя в браузере и разлогинит пользователя
                .logoutSuccessUrl("/auth/login");   // если человек разлогинился, переводим его на эту страницу

        // указываем на какую страницу перейти
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
//        return NoOpPasswordEncoder.getInstance();  // нет никакого шифрования
        return new BCryptPasswordEncoder();  // возвращаем новый объект энкодера, который будет заниматься шифрованием
    }
}
