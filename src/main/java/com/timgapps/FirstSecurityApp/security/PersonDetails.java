package com.timgapps.FirstSecurityApp.security;

import com.timgapps.FirstSecurityApp.models.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class PersonDetails implements UserDetails {

    private final Person person;

    public PersonDetails(Person person) {
        this.person = person;
    }


    // получаем коллекцию тех прав, которые есть у пользователя
    // на основании прав, будем давать доступ к определенным страницам, а к каким-то не будем давать
    // этот метод должен возвращать роль человека или список его действий в виде такой коллекции
    // и уже spring будет понимать, что у текущего пользователя такая authority(роль) и соответственно, на основании
    // этой роли мы сможем разграничивать доступ
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO: return roles(or authorities) of the user
//        return Collections.emptyList();  // здесь будем получать роли
        // если бы мы реализовывали аторизацию на основании списка, то здесь мы бы создавали какой-нибудь список
        // с обектами new SimpleGrantedAuthority(), в каждый из этих объектов мы бы помещали какую-то строку вида:
        // SHOW_ACCOUNT, WITHDRAW_MONEY, SEND_MONEY (т.е. в этом списке содержались бы действия, которые может совершать
        // текущий пользователь
        // а сейчас мы просто возвращаем список из одного элемента, в котором будет находится либо ROLE_ADMIN
        // либо ROLE_USER - это роли (для spring security - что роль, что действие - это одно и то же
        // (это просто объект класса SimpleGrantedAuthority, которому в качестве аргумента в конструктор мы передали строку
        // эта строка либо роль, либо конкретное действие, которое может совершать пользователь)
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));
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
