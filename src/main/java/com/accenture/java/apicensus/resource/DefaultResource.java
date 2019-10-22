package com.accenture.java.apicensus.resource;

import com.accenture.java.apicensus.utils.Endpoint;
import com.accenture.java.apicensus.utils.RouteID;
import org.springframework.stereotype.Component;

/**
 * @author Gian F. S.
 */
@Component
public class DefaultResource extends ExceptionCatcherResource {

    /**
     * Creates default endpoints.
     *
     * @throws Exception the exception
     */
    @Override
    public void configure() throws Exception {
        super.configure();

        // A default endpoint, used for ProducerTemplate
        from(Endpoint.DIRECT_DEFAULT_ENDPOINT).routeId(RouteID.DEFAULT_ROUTE)
            .log("I'm a default endpoint");
    }
}
