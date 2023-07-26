package com.timgapps.FirstSecurityApp.security;

import com.timgapps.FirstSecurityApp.models.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class PersonDetails implements UserDetails {

    private final Person person;

    public PersonDetails(Person person) {
        this.person = person;
    }


    // получаем коллекцию тех прав, которые есть у пользователя
    // на основании прав, будем давать доступ к определенным страницам, а к каким-то не будем давать
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;  // здесь будем получать роли
    }


    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getUsername();
    }

    // показываем, что аккаунт не просроченный, что он действительный, поэтому возвращаем true
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // показываем, что этот аккаунт не заблокирован, поэтому возвращаем true
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // показываем, что пароль не просрочен, возвращаем true
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // этот метод говорит о том, что аккаунт включен, работает
    @Override
    public boolean isEnabled() {
        return true;
    }

    // нужен, чтобы получать данные аутентифицированного пользователя
    public Person getPerson() {
        return this.person;
    }
}
