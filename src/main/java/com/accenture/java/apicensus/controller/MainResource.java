package com.accenture.java.apicensus.controller;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class MainResource extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration().bindingMode(RestBindingMode.auto);

        onException(Exception.class)
            .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("500"))
            .setBody(exchange -> exchange.getIn().getBody(Exception.class).getMessage());
    }
}
