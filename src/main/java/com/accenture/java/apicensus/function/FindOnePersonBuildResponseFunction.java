package com.accenture.java.apicensus.function;

import com.accenture.java.apicensus.entity.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mongodb.BasicDBObject;
import org.apache.camel.Exchange;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author Gian F. S.
 */
@Component
public class FindOnePersonBuildResponseFunction implements Function<Exchange, Optional> {

    private Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Returns and optional with the person.
     * <br><br>
     * If any exception occurs the optional
     * will be empty; else the optional will
     * contains the person data.
     *
     * @param exchange the exchange
     *
     * @return An optional whit the person data;
     *      but an exception occurs, an empty
     *      optional will be returned.
     */
    @Override
    public Optional apply(Exchange exchange) {
        Person person = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new Jdk8Module());
            objectMapper.registerModule(new JavaTimeModule());
            person = objectMapper.readValue(exchange.getIn().getBody(
                BasicDBObject.class).toJson(), Person.class);
        } catch (Exception e) {
            logger.error("The person doesn't exists");
        }
        return Optional.ofNullable(person);
    }
}
