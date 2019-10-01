package com.accenture.java.apicensus.utils;

import com.accenture.java.apicensus.entity.Country;
import com.accenture.java.apicensus.entity.Person;
import com.accenture.java.apicensus.service.impl.PersonFileServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class PeopleFileProvider {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Value("${file.people.prefix}")
    private String peopleFilePrefix;

    @Value("${file.people.suffix}")
    private String peopleFileSuffix;

    private HashMap<Country, List<Person>> filesMap = new HashMap<>();

    /**
     * Reads the file and returns the content
     * <br><br>
     * Reads the people file, it depends of the country param value, unmarshal it content
     * to Person class and finally save the list into the files map
     *
     * @param country
     *
     * @return the file content; if the file does not exists, an empty array
     *          is returned
     */
    public List<Person> getPeopleFileContent(Country country) {
        List<Person> people = filesMap.get(country);
        if (people == null) {
            try {
                logger.info("Before retrieve people file");
                InputStream inputStream = PersonFileServiceImpl.class.getResourceAsStream(getPeopleFilePath(country));
                logger.info("File retrieved");

                ObjectMapper objectMapper = new ObjectMapper();
                // used to converted the String representation to LocalDateTime
                objectMapper.registerModule(new JavaTimeModule());

                logger.info("Before parsing file content");
                people = Arrays.asList(objectMapper.readValue(inputStream, Person[].class));
                inputStream.close();
                logger.info("Content parsed");

                filesMap.put(country, people);
            } catch (IOException e) {
                logger.error("Fail to retrieve people file:", e);
            } catch (Exception e) {
                logger.error("Unhandled error happened", e);
            }
        }
        return people;
    }

    private String getPeopleFilePath(Country country) {
        if (country != null) {
            return peopleFilePrefix + country.name() + peopleFileSuffix;
        }
        return peopleFilePrefix + peopleFileSuffix;
    }
}
