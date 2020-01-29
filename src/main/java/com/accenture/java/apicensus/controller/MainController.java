package com.accenture.java.apicensus.controller;

import com.accenture.java.apicensus.utils.Endpoint;
import com.accenture.java.apicensus.utils.RouteID;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;

/**
 * Configure all controllers with default values,
 * with exceptions catchers.
 *
 * @author Gian F. S.
 */
public abstract class MainController extends RouteBuilder {

    @Value("${server.port}")
    private Integer serverPort;

    /**
     * Configure each MainController's sons
     *
     * @throws Exception the exception to catch
     */
    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet")
            // Mapped the request and response to json
            .bindingMode(RestBindingMode.json)
            // By default is true and it does that all
            // non 2xx status code will marshal the response to String,
            // in this case each status code will marshal the response to JSON.
            .skipBindingOnErrorCode(false)
            // output using pretty print
            .dataFormatProperty("prettyPrint", "true")
            .apiHost("localhost")
            .port(serverPort)
            .contextPath("/census")
            .apiContextPath("/swagger")
            .apiContextRouteId(RouteID.SWAGGER.id())
            .apiProperty("api.title", "API Census")
            .apiProperty("api.description", "Application responsible for returning the person's information by ssn.")
            .apiProperty("api.version", "0.0.1-SNAPSHOT")
            .apiProperty("api.contact.name", "Gian F. S.");

        onException(Exception.class).handled(true)
            .to(Endpoint.DIRECT_INTERNAL_SERVER_ERROR);
    }
}
