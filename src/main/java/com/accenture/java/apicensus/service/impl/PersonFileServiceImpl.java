package com.accenture.java.apicensus.service.impl;

import com.accenture.java.apicensus.entity.Country;
import com.accenture.java.apicensus.entity.Person;
import com.accenture.java.apicensus.service.IPersonService;
import com.accenture.java.apicensus.utils.PeopleFileProvider;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier(value = "personFileService")
public class PersonFileServiceImpl implements IPersonService {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private PeopleFileProvider peopleFileProvider;

    @Override
    public Optional<Person> save(@Valid Person person) {
        return Optional.empty();
    }

    /**
     * Returns an optional object with a person instance.
     *
     * @param ssn
     * @param country
     *
     * @return If the person was found, returns an optional object with it;
     *          otherwise an empty optional.
     */
    @Override
    public Optional<Person> findOneBySsnAndCountry(Integer ssn, Country country) {
        logger.info("Into PersonFileServiceImpl::findById(Long, String)");
        logger.debug("ssn[" + ssn + "], country[" + country + "]");

        List<Person> persons = peopleFileProvider.getPeopleFileContent(country);
        return persons.stream().filter(person -> person.getSsn().equals(ssn)).findFirst();
    }
}
