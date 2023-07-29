package com.timgapps.FirstSecurityApp.services;


import com.timgapps.FirstSecurityApp.models.Person;
import com.timgapps.FirstSecurityApp.repositories.PeopleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RegistrtionService {
    private final PeopleRepository peopleRepository;

    private PasswordEncoder passwordEncoder;

    public RegistrtionService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // в э
    @Transactional  // аннотация, потому что происходит изменение в базе данных
    public void register(Person person) {
        // зашифруем пароль с формы и сохраним в пароле человека
        String encodedPassword = passwordEncoder.encode(person.getPassword());
        person.setPassword(encodedPassword);

        peopleRepository.save(person);
    }
}
