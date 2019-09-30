package com.accenture.java.apicensus.service;

import com.accenture.java.apicensus.entity.Country;
import com.accenture.java.apicensus.entity.Person;

import java.util.Optional;

public interface IPersonService {
    Optional<Person> findById(Integer ssn, Country country);
}
