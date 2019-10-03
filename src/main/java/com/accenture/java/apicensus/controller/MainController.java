package com.accenture.java.apicensus.controller;

import org.apache.camel.TypeConversionException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class MainController extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration().bindingMode(RestBindingMode.auto);

        onException(Exception.class)
            .to("direct:internalServerError");

        onException(TypeConversionException.class)
            .to("direct:badRequest");
    }
}
