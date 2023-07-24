package com.timgapps.FirstSecurityApp.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

// в этом провайдере будет следующая логика: мы будем смотреть на базу данных, смотреть на таблицу Person
// и сравнивать пароль, который был введен с формы с паролем, который лежит в таблице
@Component
public class AuthProviderImpl implements AuthenticationProvider {


    // возвращает объект Principal(объект с данными о пользователе, будет лежать объект с PersonDetails)
    // получает в качестве аргумента получает Authentication, в котором лежат логин и пароль
    // этот метод вызывается самим Security и сам Security будет передавать нам в качестве
    // аргумента объект Authentication
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();

        // с помощью hibernate и data jpa найдем пользователя по имени
        return null;
    }

    // метод чисто технический, нужен spring'у чтобы
    // понять для какого объекта Authentication работает AuthenticationProvider
    @Override
    public boolean supports(Class<?> authentication) {
        return true;  // пока установим значение true, чтобы обозначить что этот провайдер для всех случаев хорош
        // если в приложении несколько authentication провайдеров,
        // то в этом методе мы можем сказать для каких сценариев какой провайдер мы можем использовать
    }
}
