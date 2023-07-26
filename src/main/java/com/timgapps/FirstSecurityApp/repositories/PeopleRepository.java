package com.timgapps.FirstSecurityApp.repositories;

import com.timgapps.FirstSecurityApp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    // Optional<> говорит о том, что человек может быть найден, а может быть не найден
    Optional<Person> findByUsername(String username);
}
