package com.accenture.java.apicensus.function;

import com.accenture.java.apicensus.entity.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import org.apache.camel.Exchange;
import org.apache.camel.InvalidPayloadException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bson.json.JsonWriterSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Gian F. S.
 */
@Component
public class FindOnePersonBuildResponseFunction implements Function<Exchange, Optional> {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JsonWriterSettings jsonWriterSettings;

    /**
     * Returns and optional with the person.
     * <br><br>
     * If any exception occurs the optional
     * will be empty; else the optional will
     * contains the person data.
     *
     * @param exchange the exchange
     * @return An optional whit the person data;
     * but an exception occurs, an empty
     * optional will be returned.
     */
    @Override
    public Optional apply(Exchange exchange) {
        Person person = null;

        if (Objects.nonNull(exchange)) {
            Logger logger = LogManager.getLogger(this.getClass());
            try {
                Optional<BasicDBObject> optionalDBObject = exchange.getIn()
                    .getMandatoryBody(Optional.class);
                if (optionalDBObject.isPresent()) {
                    person = objectMapper.readValue(optionalDBObject.get()
                        .toJson(jsonWriterSettings), Person.class);
                }
            } catch (InvalidPayloadException exception) {
                logger.debug("The exchange body is null");
            } catch (Exception exception) {
                logger.error("The person doesn't exist");
            }
        }

        return Optional.ofNullable(person);
    }
}
