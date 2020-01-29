package com.accenture.java.apicensus.resource;

import com.accenture.java.apicensus.entity.dto.PersonDTO;
import com.accenture.java.apicensus.exception.InvalidPersonFieldException;
import com.accenture.java.apicensus.exception.PersonInsertionException;
import com.accenture.java.apicensus.exception.UnexpectedException;
import com.accenture.java.apicensus.mapper.PersonMapper;
import com.accenture.java.apicensus.utils.Endpoint;
import com.accenture.java.apicensus.utils.RouteID;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

/**
 * @author Gian F. S.
 */
@Component
public class CommonResource extends ExceptionCatcherResource {

    @Override
    public void configure() throws Exception {
        super.configure();

        // A default endpoint, used for ProducerTemplate
        from(Endpoint.DIRECT_DEFAULT_ENDPOINT).routeId(RouteID.DEFAULT_ROUTE.id())
            .log("I'm a default endpoint");

        from(Endpoint.DIRECT_DO_INSERT_PEOPLE).routeId(RouteID.DO_INSERT_PEOPLE.id())
            .unmarshal().json(JsonLibrary.Jackson, PersonDTO[].class)
            .log("New file was added")
            .to(Endpoint.BEAN_VALIDATOR_DEFAULT_GROUP)
            // Convert the body from PersonDTO to Person
            .bean(PersonMapper.class, "toEntity(java.util.List)")
            .split(body())
                .doTry()
                    .to(Endpoint.DIRECT_INSERT_DB_PERSON)
                .doCatch(InvalidPersonFieldException.class)
                    .log("Invalid field")
                .doCatch(PersonInsertionException.class)
                    .log("Duplicated person constraint")
                .doCatch(UnexpectedException.class)
                    .log("Unexpected error occurs")
                .endDoTry()
            .end();
    }
}
