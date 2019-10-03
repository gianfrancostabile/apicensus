package com.accenture.java.apicensus.repository;

import com.accenture.java.apicensus.entity.Country;
import com.accenture.java.apicensus.entity.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends MongoRepository<Person, Integer> {
    Optional<Person> findTop1BySsnAndCountry(Integer ssn, Country country);
}
