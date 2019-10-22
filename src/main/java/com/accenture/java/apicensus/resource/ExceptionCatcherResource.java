package com.accenture.java.apicensus.resource;

import com.accenture.java.apicensus.utils.Endpoint;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.apache.camel.builder.RouteBuilder;

public abstract class ExceptionCatcherResource extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        onException(MismatchedInputException.class)
            .handled(true)
            .to(Endpoint.DIRECT_MISMATCHED_INPUT_FILE);
    }
}
