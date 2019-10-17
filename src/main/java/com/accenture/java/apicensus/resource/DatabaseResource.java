package com.accenture.java.apicensus.resource;

import com.accenture.java.apicensus.entity.Country;
import com.accenture.java.apicensus.entity.Person;
import com.accenture.java.apicensus.entity.dto.FindOnePersonDTO;
import com.accenture.java.apicensus.utils.Endpoint;
import com.accenture.java.apicensus.utils.RouteID;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.apache.camel.component.bean.validator.BeanValidationException;
import org.apache.camel.component.mongodb.MongoDbConstants;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Creates all camel endpoints related to database
 *
 * @author Gian F. S.
 */
@Component
public class DatabaseResource extends ExceptionCatcherResource {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void configure() throws Exception {
        super.configure();

        // Saves the person into the database
        from(Endpoint.DIRECT_INSERT_DB_PERSON)
            .routeId(RouteID.MONGODB_PERSON_INSERT)
            .to(Endpoint.BEAN_VALIDATOR_DEFAULT_GROUP)
            .to(Endpoint.MONGODB_PERSON_INSERT)
            .log("A Person was added. ${in.body}");

        // Search the person by ssn and country
        from(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY)
            .routeId(RouteID.MONGODB_PERSON_FINDONEBY_SSNANDCOUNTRY)
            .choice()
                // Validates that the body is a FindOnePersonDTO instance
                .when(bodyAs(FindOnePersonDTO.class).isNull())
                    .setBody(exchange -> Optional.empty())
                .otherwise()
                    .doTry()
                        // Validates each FindOnePersonDTO fields and throws
                        // BeanValidationException if any field is invalid
                        .to(Endpoint.BEAN_VALIDATOR_DEFAULT_GROUP).to(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY_CREATEREQUEST)
                        // Finds the person
                        .to(Endpoint.MONGODB_PERSON_FINDONEBY_SSNANDCOUNTRY)
                        .to(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY_CREATERESPONSE)
                    .doCatch(BeanValidationException.class)
                        .log("Some fields are nulls: ssn[${body.ssn}] - country[${body.country}]")
                        .setBody(exchange -> Optional.empty())
                    .endDoTry()
            .end();

        // Sets the request body and header to mongodb request
        from(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY_CREATEREQUEST)
            .routeId(RouteID.FINDONEBY_SSNANDCOUNTRY_REQUEST)
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
            });

        // Parse the body to Optional with a person instance into it
        from(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY_CREATERESPONSE)
            .routeId(RouteID.FINDONEBY_SSNANDCOUNTRY_RESPONSE)
            .setBody(exchange -> {
                Person person = null;
                try {
                    BasicDBObject basicDBObject = exchange.getIn().getBody(BasicDBObject.class);
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.registerModule(new Jdk8Module());
                    objectMapper.registerModule(new JavaTimeModule());
                    person = objectMapper.readValue(basicDBObject.toJson(), Person.class);
                } catch (Exception e) {
                    logger.error("The person doesn't exists");
                }
                return Optional.ofNullable(person);
            });

        from(Endpoint.DIRECT_FINDONEBYQUERY_SUCCESS)
            .routeId(RouteID.DIRECT_FINDONEBYQUERY_SUCCESS)
            .setBody(exchange ->
                new BasicDBObject().append("ssn", 11111111).append("name", "Luis").append("surname", "Malo")
                .append("bornDate", "17-04-1986").append("country", Country.UY.name()).append("genre", "Male"));

        from(Endpoint.DIRECT_FINDONEBYQUERY_FAIL)
            .routeId(RouteID.DIRECT_FINDONEBYQUERY_FAIL)
            .setBody(exchange -> null);
    }
}
