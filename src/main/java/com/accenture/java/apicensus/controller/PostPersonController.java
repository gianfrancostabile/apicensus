package com.accenture.java.apicensus.controller;

import com.accenture.java.apicensus.processor.FindPeopleProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

/**
 * @author Gian F. S.
 */
@Controller
public class PostPersonController extends MainController {

    @Autowired
    private FindPeopleProcessor findPeopleProcessor;

    @Override
    public void configure() throws Exception {
        super.configure();

        rest()
            // POST rest method to find the requested people
            // Needs the country header and an array of Ssn
            // Country as String and each Ssn as Integer
            .post()
                .consumes(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .produces(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .route()
                    .process(findPeopleProcessor)
            .endRest();
    }
}
