package com.accenture.java.apicensus.resource;

import com.accenture.java.apicensus.utils.Endpoint;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author Gian F. S.
 */
public abstract class ExceptionCatcherResource extends RouteBuilder {

    /**
     * Creates a MismatchedException handler.
     *
     * @throws Exception the exception
     */
    @Override
    public void configure() throws Exception {
        onException(MismatchedInputException.class)
            .handled(true)
            .to(Endpoint.DIRECT_MISMATCHED_INPUT_FILE);
    }
}
