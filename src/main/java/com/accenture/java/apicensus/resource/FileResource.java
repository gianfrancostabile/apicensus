package com.accenture.java.apicensus.resource;

import com.accenture.java.apicensus.entity.dto.PersonDTO;
import com.accenture.java.apicensus.processor.InsertPersonProcessor;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Creates all camel endpoint related to files.
 *
 * @author Gian F. S.
 */
@Component
public class FileResource extends RouteBuilder {

    @Value("${file.people.ftp.folder}")
    private String folder;

    @Value("${file.people.ftp.file}")
    private String file;

    @Autowired
    private InsertPersonProcessor insertPersonProcessor;

    @Override
    public void configure() throws Exception {
        /*
            Works like Observer-Observable pattern, if a new file is added
            inside the folder, it will be processed.

            With the attribute delete in true, at the end, the file will
            be deleted. And antInclude determinate the naming convention
            of the file.
         */
        from("file:" + folder + "?delete=true&antInclude=" + file)
            .doTry()
                .unmarshal().json(JsonLibrary.Jackson, PersonDTO[].class)
                .log("New file was added into " + folder)
                .split(body())
                    .process(insertPersonProcessor)
                .end()
            .endDoTry()
            .doCatch(MismatchedInputException.class)
                .log("Cannot deserialize the file content to Person[] object: ")
                .log("${body}")
            .endDoTry();
    }
}
