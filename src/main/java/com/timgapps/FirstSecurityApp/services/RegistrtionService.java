package com.timgapps.FirstSecurityApp.services;


import com.timgapps.FirstSecurityApp.models.Person;
import com.timgapps.FirstSecurityApp.repositories.PeopleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Transient;

@Service
public class RegistrtionService {
    private final PeopleRepository peopleRepository;

    public RegistrtionService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    // в э
    @Transactional  // аннотация, потому что происходит изменение в базе данных
    public void register(Person person) {
        person.setRole("ROLE_USER");  // имя роли всегда долно начинаться со слова "ROLE_",
        //  только тогда spring будет понимать, что это роль
        peopleRepository.save(person);

    }
}
