package com.accenture.java.apicensus.controller;

import com.accenture.java.apicensus.processor.GetPeopleProcessor;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

@Controller
public class PostPersonController extends MainController {

    @Autowired
    private GetPeopleProcessor getPeopleProcessor;

    @Override
    public void configure() throws Exception {
        super.configure();

        rest()
            .post()
                .bindingMode(RestBindingMode.json)
                .consumes(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .produces(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .route()
                    .process(getPeopleProcessor)
            .endRest();
    }
}
