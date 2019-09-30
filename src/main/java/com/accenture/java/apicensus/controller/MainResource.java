package com.accenture.java.apicensus.controller;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class MainResource extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration().port(9000).bindingMode(RestBindingMode.auto);
        onException(Exception.class)
            .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("500"))
            .setBody(exchange -> "Something was wrong");
    }
}
