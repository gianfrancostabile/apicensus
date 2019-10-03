package com.accenture.java.apicensus.processor;

import com.accenture.java.apicensus.entity.Person;
import com.accenture.java.apicensus.service.IPersonService;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SavePersonProcessor implements Processor {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    @Qualifier(value = "personService")
    private IPersonService iPersonService;

    @Override
    public void process(Exchange exchange) throws Exception {
        Person person = exchange.getIn().getBody(Person.class);

        if (person != null) {
            try {
                logger.info("Trying to save a new person with Ssn[" + person.getSsn() +
                    "] Country[" + person.getCountry().name() + "]");
                Optional<Person> optionalPerson = iPersonService.save(person);
                logger.info(optionalPerson.isPresent()
                    ? "Person saved"
                    : "Person couldn't be saved");
            } catch (Exception exc) {
                logger.error(exc.getMessage());
            }
        } else {
            logger.error("The person is null");
        }
    }
}
