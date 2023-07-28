package com.timgapps.FirstSecurityApp.util;


import com.timgapps.FirstSecurityApp.models.Person;
import com.timgapps.FirstSecurityApp.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonDetailsService personDetailsService;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    // говорит, что этот валидатор нужен для объектов класса Person
    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        // используя personDetailService произведем валидацию
        try {
            personDetailsService.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException ignored) {
            // немного не правильный подход, но мы так сделаем:
            // в случае, если loadUserByUsername выбросит исключение, значит валидация прошла успешно,
            // значит такой человек не найден
            return;   // Всё ок, пользователь с таким именем не найден
        }
        errors.rejectValue("username", "", "Человек с таким именем пользователя уже существует");
    }
}
