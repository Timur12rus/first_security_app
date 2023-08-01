package com.timgapps.FirstSecurityApp.services;


import com.timgapps.FirstSecurityApp.models.Person;
import com.timgapps.FirstSecurityApp.repositories.PeopleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrtionService {

    // внедрим passwordEncoder для шифрования паролей
    private final PasswordEncoder passwordEncoder;
    private final PeopleRepository peopleRepository;

    public RegistrtionService(PasswordEncoder passwordEncoder, PeopleRepository peopleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.peopleRepository = peopleRepository;
    }

    // в э
    @Transactional  // аннотация, потому что происходит изменение в базе данных
    public void register(Person person) {
        // имя роли всегда долно начинаться со слова "ROLE_",
        // только тогда spring будет понимать, что это роль
        person.setRole("ROLE_USER");

        // получим зашифрованный пароль
        String encodedPassword = passwordEncoder.encode(person.getPassword());
        person.setPassword(encodedPassword);

        peopleRepository.save(person);

    }
}
