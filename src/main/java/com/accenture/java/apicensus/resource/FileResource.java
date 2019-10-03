package com.accenture.java.apicensus.resource;

import com.accenture.java.apicensus.entity.Person;
import com.accenture.java.apicensus.processor.SavePersonProcessor;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileResource extends RouteBuilder {

    @Autowired
    private SavePersonProcessor savePersonProcessor;

    @Value("${file.people.ftp.folder}")
    private String folder;

    @Value("${file.people.ftp.file}")
    private String file;

    @Override
    public void configure() throws Exception {
        from("file:" + folder + "?delete=true&antInclude=" + file)
            .doTry()
                .unmarshal().json(JsonLibrary.Jackson, Person[].class)
                .log("New file was added into " + folder)
                .split(body())
                    .process(savePersonProcessor)
                .end()
            .endDoTry()
            .doCatch(MismatchedInputException.class)
                .log("Cannot deserialize the file content to Person[] object: ")
                .log("${body}")
            .endDoTry();
    }
}
