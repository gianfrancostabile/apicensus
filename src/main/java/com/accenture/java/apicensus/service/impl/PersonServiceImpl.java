package com.accenture.java.apicensus.service.impl;

import com.accenture.java.apicensus.entity.Person;
import com.accenture.java.apicensus.service.IPersonService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier(value = "personService")
public class PersonServiceImpl implements IPersonService {

    @Override
    public Optional<Person> findById(Integer ssn, String country) {
        List<Person> persons = new ArrayList<>();
        switch (country) {
            case "AR":
                persons.add(new Person(12345678, "Luis", "Perez", LocalDateTime.now(), "Argentina", "Male"));
                persons.add(new Person(56784356, "Rodrigo", "Limus", LocalDateTime.now(), "Argentina", "Male"));
                break;
            case "CH":
                persons.add(new Person(56233415, "Mariana", "Sanchez", LocalDateTime.now(), "Chile", "Female"));
                break;
            case "BR":
                persons.add(new Person(23985674, "Marisa", "Perez", LocalDateTime.now(), "Brazil", "Female"));
                break;
            case "UY":
                persons.add(new Person(87654321, "Ribair", "Rodriguez", LocalDateTime.now(), "Uruguay", "Male"));
                break;
        }
        return persons.stream().filter(person -> person.getSsn().equals(ssn)).findFirst();
    }
}
