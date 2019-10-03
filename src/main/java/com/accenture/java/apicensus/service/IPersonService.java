package com.accenture.java.apicensus.service;

import com.accenture.java.apicensus.entity.Country;
import com.accenture.java.apicensus.entity.Person;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@Validated
public interface IPersonService {
    Optional<Person> save(@Valid Person person);
    Optional<Person> findOneBySsnAndCountry(Integer ssn, Country country);
}
