package com.accenture.java.apicensus.controller;

import com.accenture.java.apicensus.entity.Country;
import com.accenture.java.apicensus.entity.dto.ResponseListDTO;
import com.accenture.java.apicensus.processor.FindPeopleProcessor;
import com.accenture.java.apicensus.utils.RouteID;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Gian F. S.
 */
@Controller
public class PostPersonController extends MainController {

    @Autowired
    private FindPeopleProcessor findPeopleProcessor;

    /**
     * Creates POST rest method to find the requested people
     * Needs the country header and an array of Ssn
     * Country as String and each Ssn as Integer
     *
     * @throws Exception the exception to catch
     */
    @Override
    public void configure() throws Exception {
        super.configure();

        rest()
            .post("/")
                .outType(ResponseListDTO.class)
                .description("Returns the person information by ssn")
                .param()
                    .name("country").type(RestParamType.header)
                    .dataType("string").description("The country of people to look for")
                    .allowableValues(
                        Stream.of(Country.values()).map(Country::name).collect(Collectors.toList()))
                    .required(true)
                .endParam()
                // TODO make swagger knows the examples
                .responseMessage()
                    .code(200).responseModel(ResponseListDTO.class).message(
                        "All ssn were valid and the information of the people was obtained.")
                    .example("200",
                        "{\n  \"successList\": [\n    {\n      \"ssn\": 0,\n      \"name\": \"string\",\n      \"surname\": \"string\",\n      \"bornDate\": \"2019-10-10\",\n      \"country\": \"AR\",\n      \"genre\": \"string\"\n    }\n  ]\n}")
                .endResponseMessage()
                .responseMessage()
                    .code(207).responseModel(ResponseListDTO.class).message(
                    "Some of the ssn were valid and the information of all the people was not obtained.")
                    .example("207",
                        "{\n  \"successList\": [\n    {\n      \"ssn\": 0,\n      \"name\": \"string\",\n      \"surname\": \"string\",\n      \"bornDate\": \"2019-10-10\",\n      \"country\": \"AR\",\n      \"genre\": \"string\"\n    }\n  ],\n  \"errorList\": [\n    {}\n  ]\n}")
                .endResponseMessage()
                .responseMessage()
                    .code(400).responseModel(ResponseListDTO.class).message("No ssn obtained to process.")
                    .example("400", "{}")
                .endResponseMessage()
                .responseMessage()
                    .code(404).responseModel(ResponseListDTO.class).message(
                    "All ssn were not valid and the information of the people was not obtained.")
                    .example("404", "{\n  \"errorList\": [\n    {}\n  ]\n}")
                .endResponseMessage()
                .consumes(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .produces(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .type(List.class)
                .route().routeId(RouteID.PERSON_POST)
                    .process(findPeopleProcessor)
            .endRest();
    }
}
