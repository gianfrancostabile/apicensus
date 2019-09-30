package com.accenture.java.apicensus.service.impl;

import com.accenture.java.apicensus.entity.Person;
import com.accenture.java.apicensus.service.IPersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier(value = "personFileService")
public class PersonFileServiceImpl implements IPersonService {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public Optional<Person> findById(Integer ssn, String country) {
        logger.info("Into PersonFileServiceImpl::findById(Long, String)");
        logger.debug("ssn[" + ssn + "], country[" + country + "]");
        try {
            logger.info("Before retrieve persons.json file");
            File file = new File("src/main/resources/persons.json");
            logger.info("File retrieved");

            logger.info("Before parsing file content");
            List<Person> persons = new ObjectMapper().readValue(file, List.class);
            logger.debug("File content" + persons.toString());
            logger.info("Content parsed");

            return persons.stream().filter(person -> person.getSsn().equals(ssn)).findFirst();
        } catch (IOException e) {
            logger.error("Fail to retrieve persons.json file:", e);
        } catch (Exception e) {
            logger.error("Unhandled error happened", e);
        }
        return Optional.empty();
    }
}
