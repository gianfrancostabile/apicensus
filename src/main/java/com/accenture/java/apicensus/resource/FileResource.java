package com.accenture.java.apicensus.resource;

import com.accenture.java.apicensus.entity.dto.PersonDTO;
import com.accenture.java.apicensus.mapper.PersonMapper;
import com.accenture.java.apicensus.utils.Endpoint;
import com.accenture.java.apicensus.utils.RouteID;
import com.mongodb.MongoWriteException;
import org.apache.camel.component.bean.validator.BeanValidationException;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

/**
 * Creates all camel endpoint related to files.
 *
 * @author Gian F. S.
 */
@Component
public class FileResource extends ExceptionCatcherResource {

    @Override
    public void configure() throws Exception {
        super.configure();

        /*
            Works like Observer-Observable pattern, if a new file is added
            inside the folder, it will be processed.

            With the attribute delete in true, at the end, the file will
            be deleted. And antInclude determinate the naming convention
            of the file.
         */
        from(Endpoint.FILE_PERSON_FTP).routeId(RouteID.FILE_PERSON_FTP)
            .unmarshal().json(JsonLibrary.Jackson, PersonDTO[].class)
            .log("New file was added")
            // Convert the body from PersonDTO to Person
            .to(Endpoint.BEAN_VALIDATOR_DEFAULT_GROUP)
            .bean(PersonMapper.class, "toEntity(java.util.List)")
            .split(body())
                // I don't want to catch these exception in an global catcher, I want to continue the file process
                .doTry()
                    .to(Endpoint.DIRECT_INSERT_DB_PERSON)
                .doCatch(BeanValidationException.class)
                    .log("The person has null fields.")
                .doCatch(MongoWriteException.class)
                    .log("The person already exists.").doCatch(Exception.class)
                    .log("A person is invalid, it has fields in null. ${in.body}")
                .endDoTry()
            .end();
    }
}
