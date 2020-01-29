package com.accenture.java.apicensus.controller;

import com.accenture.java.apicensus.entity.Country;
import com.accenture.java.apicensus.function.AllCountriesSupplier;
import com.accenture.java.apicensus.utils.RouteID;
import com.accenture.java.apicensus.utils.Tag;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestDefinition;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

/**
 * @author Gian F. S.
 */
@Controller
public class GetCountryController extends MainController {

    @Autowired
    private AllCountriesSupplier allCountriesSupplier;

    /**
     * Creates a GET rest method that returns the allow countries
     *
     * @throws Exception the exception to catch
     *
     * @see MainController
     * @see RouteBuilder#rest()
     * @see RestDefinition#get(String)
     * @see Country
     */
    @Override
    public void configure() throws Exception {
        super.configure();

        rest()
            .get("/countries")
                .tag(Tag.GET.name())
                .description("Returns all the allowed countries")
                .param()
                    .name("Authorization")
                    .dataType("string")
                    .description("JWT authenticated token")
                    .defaultValue("Bearer XXXX.XXXX.XXXX")
                    .type(RestParamType.header)
                    .required(true)
                .endParam()
                .responseMessage()
                    .code(200)
                    .responseModel(Country[].class)
                    .message("All allowed countries.")
                .endResponseMessage()
                .produces(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .route().routeId(RouteID.COUNTRIES_GET.id())
                    .setBody(allCountriesSupplier)
            .endRest();
    }
}
