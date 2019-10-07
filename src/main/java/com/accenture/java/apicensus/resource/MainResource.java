package com.accenture.java.apicensus.resource;

import com.accenture.java.apicensus.entity.Endpoint;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * @author Gian F. S.
 */
@Component
public class MainResource extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // A default endpoint, used for ProducerTemplate
        from(Endpoint.DIRECT_DEFAULT_ENDPOINT.endpoint())
            .log("I'm a default endpoint");
    }
}
