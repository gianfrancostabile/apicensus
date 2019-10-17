package com.accenture.java.apicensus.controller;

import com.accenture.java.apicensus.entity.Country;
import com.accenture.java.apicensus.utils.RouteID;
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
            .get("/countries").description("Returns all the allowed countries")
            // Defines all responses with their status code, model type and example to Swagger
            // TODO make swagger knows the examples
            .responseMessage()
                .code(200).responseModel(Country[].class).message("All allowed countries.")
            .endResponseMessage()
            .produces(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .route().routeId(RouteID.COUNTRIES_GET)
                .setBody(exchange -> Country.values())
            .endRest();
    }
}
