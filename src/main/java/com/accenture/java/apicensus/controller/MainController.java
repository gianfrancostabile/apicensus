package com.accenture.java.apicensus.controller;

import com.accenture.java.apicensus.entity.Endpoint;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

/**
 * Configure all controllers with default values,
 * with exceptions catchers.
 *
 * @author Gian F. S.
 */
public class MainController extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration()
            // Mapped the request and response to json
            .bindingMode(RestBindingMode.json)
            // By default is true and it does that all
            // non 2xx status code will marshal the response to String,
            // in this case each status code will marshal the response to JSON.
            .skipBindingOnErrorCode(false);

        onException(Exception.class)
            .to(Endpoint.DIRECT_INTERNAL_SERVER_ERROR.endpoint());
    }
}
