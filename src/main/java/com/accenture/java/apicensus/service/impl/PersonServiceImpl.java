package com.accenture.java.apicensus.service.impl;

import com.accenture.java.apicensus.entity.Country;
import com.accenture.java.apicensus.entity.Person;
import com.accenture.java.apicensus.repository.PersonRepository;
import com.accenture.java.apicensus.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@Service
@Qualifier(value = "personService")
public class PersonServiceImpl implements IPersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Optional<Person> save(@Valid Person person) {
        Person result = null;
        if (!personRepository.findTop1BySsnAndCountry(person.getSsn(), person.getCountry()).isPresent()) {
            result = personRepository.save(person);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Person> findOneBySsnAndCountry(Integer ssn, Country country) {
        return personRepository.findTop1BySsnAndCountry(ssn, country);
    }
}
