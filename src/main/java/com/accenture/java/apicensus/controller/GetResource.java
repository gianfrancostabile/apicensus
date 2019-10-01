package com.accenture.java.apicensus.controller;

import com.accenture.java.apicensus.entity.Country;
import com.accenture.java.apicensus.entity.ResponseList;
import com.accenture.java.apicensus.processor.GetPeopleProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.TypeConversionException;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import javax.print.attribute.standard.Media;
import java.util.Arrays;
import java.util.List;

@Controller
public class GetResource extends MainResource {

    @Autowired
    private GetPeopleProcessor getPeopleProcessor;

    @Override
    public void configure() throws Exception {
        super.configure();

        rest()
            .get("/countries")
                .produces(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .route()
                    .setBody(exchange -> Country.values())
            .endRest()
            .post()
                .consumes(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .produces(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .route()
                    .doTry()
                        .process(getPeopleProcessor)
                    .endDoTry()
                    .doCatch(TypeConversionException.class)
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("400"))
                        .setBody(exchange -> {
                            ResponseList responseList = new ResponseList();
                            responseList.addError(exchange.getIn().getBody(Object[].class));
                            return responseList;
                        })
            .endRest();
    }
}
