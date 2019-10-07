package com.accenture.java.apicensus.controller;

import com.accenture.java.apicensus.entity.Country;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

/**
 * @author Gian F. S.
 */
@Controller
public class GetCountryController extends MainController {

    @Override
    public void configure() throws Exception {
        super.configure();

        rest()
            // GET rest method that returns the allow countries
            .get("/countries")
                .produces(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .route()
                    .setBody(exchange -> Country.values())
            .endRest();
    }
}
