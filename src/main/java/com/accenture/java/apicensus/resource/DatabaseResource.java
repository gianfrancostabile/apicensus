package com.accenture.java.apicensus.resource;

import com.accenture.java.apicensus.entity.Endpoint;
import com.accenture.java.apicensus.entity.Person;
import com.accenture.java.apicensus.entity.dto.FindOnePersonDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mongodb.MongoDbConstants;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Creates all camel endpoints related to database
 *
 * @author Gian F. S.
 */
@Component
public class DatabaseResource extends RouteBuilder {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Value("${camel.endpoint.mongodb.people}")
    private String personMongoDBEndpoint;

    @Override
    public void configure() throws Exception {

        // Saves the person into the database
        from(Endpoint.DIRECT_INSERT_DB_PERSON.endpoint())
            .to(personMongoDBEndpoint + "&operation=insert")
            .log("A Person was added. ${in.body}");

        // Search the person by ssn and country
        from(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY.endpoint())
            // Defines the document's fields that won't be returned
            .process(exchange -> {
                DBObject dbObject = BasicDBObjectBuilder.start()
                    .add("_id", 0).get();

                exchange.getIn().setHeader(MongoDbConstants.FIELDS_FILTER, dbObject);
            })
            // Changes the body to an entity that MongoDB used to filter
            .setBody(exchange -> {
                FindOnePersonDTO findOnePersonDTO = exchange.getIn().getBody(FindOnePersonDTO.class);

                return BasicDBObjectBuilder.start()
                    .add("ssn", findOnePersonDTO.getSsn())
                    .add("country", findOnePersonDTO.getCountry()).get();
            })
            // Finds the person
            .to(personMongoDBEndpoint + "&operation=findOneByQuery")
            // Parse the body to Optional with a person instance into it
            .setBody(exchange -> {
                Person person = null;
                try {
                    BasicDBObject basicDBObject = exchange.getIn().getBody(BasicDBObject.class);
                    person = new ObjectMapper().readValue(basicDBObject.toJson(), Person.class);
                } catch (Exception e) {
                    logger.error("The person doesn't exists");
                }
                return Optional.ofNullable(person);
            });
    }
}
