package com.accenture.java.apicensus.resource;

import com.accenture.java.apicensus.utils.Endpoint;
import com.accenture.java.apicensus.utils.RouteID;
import org.springframework.stereotype.Component;

/**
 * @author Gian F. S.
 */
@Component
public class ActiveMQResource extends ExceptionCatcherResource {

    /**
     * Creates an activemq endpoint that
     * redirects the request to another route
     * to handle it.
     *
     * @throws Exception the exception
     */
    @Override
    public void configure() throws Exception {
        super.configure();

        from(Endpoint.ACTIVEMQ_INSERT_PEOPLE).routeId(RouteID.ACTIVEMQ_INSERT_PEOPLE.id())
            .to(Endpoint.DIRECT_DO_INSERT_PEOPLE);
    }
}
