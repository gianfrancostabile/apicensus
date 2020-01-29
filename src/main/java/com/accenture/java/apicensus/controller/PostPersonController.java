package com.accenture.java.apicensus.controller;

import com.accenture.java.apicensus.entity.Country;
import com.accenture.java.apicensus.entity.dto.PersonDTO;
import com.accenture.java.apicensus.entity.dto.ResponseListDTO;
import com.accenture.java.apicensus.processor.FindPeopleProcessor;
import com.accenture.java.apicensus.utils.RouteID;
import com.accenture.java.apicensus.utils.Tag;
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
                .tag(Tag.POST.name())
                .outType(ResponseListDTO.class)
                .description("Returns the person information by ssn")
                .param()
                    .name("country")
                    .type(RestParamType.header)
                    .dataType("string")
                    .description("The country of people to look for")
                    .allowableValues(
                        Stream.of(Country.values()).map(Country::name).collect(Collectors.toList()))
                    .required(true)
                .endParam()
                // TODO make swagger knows the examples
                .responseMessage()
                    .code(200)
                    .responseModel(PersonDTO[].class)
                    .message("All ssn were valid and the information of the people was obtained.")
                .endResponseMessage()
                .responseMessage()
                    .code(207)
                    .responseModel(ResponseListDTO.class)
                    .message(
                        "Some of the ssn were valid and the information of all the people was not obtained.")
                .endResponseMessage()
                .responseMessage()
                    .code(400)
                    .message("No ssn obtained to process.")
                .endResponseMessage()
                .responseMessage()
                    .code(404)
                    .responseModel(Long[].class)
                    .message(
                        "All ssn were not valid and the information of the people was not obtained.")
                .endResponseMessage()
                .param()
                    .name("Authorization")
                    .dataType("string")
                    .description("JWT authenticated token")
                    .defaultValue("Bearer XXXX.XXXX.XXXX")
                    .type(RestParamType.header)
                    .required(true)
                .endParam()
                .consumes(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .produces(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .type(List.class)
                .outType(ResponseListDTO.class)
                .route().routeId(RouteID.PERSON_POST.id())
                    .process(findPeopleProcessor)
            .endRest();
    }
}
