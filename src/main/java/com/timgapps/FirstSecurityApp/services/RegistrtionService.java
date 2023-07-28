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
        peopleRepository.save(person);
    }
}
