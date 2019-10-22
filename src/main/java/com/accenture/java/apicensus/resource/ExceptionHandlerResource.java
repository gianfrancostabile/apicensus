package com.accenture.java.apicensus.resource;

import com.accenture.java.apicensus.function.EmptyResponseListSupplier;
import com.accenture.java.apicensus.utils.Endpoint;
import com.accenture.java.apicensus.utils.RouteID;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Creates all camel endpoints to set the
 * body after an exception occurs.
 *
 * @author Gian F. S.
 */
@Component
public class ExceptionHandlerResource extends ExceptionCatcherResource {

    @Autowired
    private EmptyResponseListSupplier emptyResponseListSupplier;

    @Override
    public void configure() throws Exception {
        super.configure();

        // Handle the INTERNAL SERVER ERROR
        from(Endpoint.DIRECT_INTERNAL_SERVER_ERROR)
            .routeId(RouteID.INTERNAL_SERVER_ERROR)
            .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("500"))
            .to(Endpoint.DIRECT_SET_EMPTY_RESPONSELIST);

        // Handle the BAD REQUEST
        from(Endpoint.DIRECT_BAD_REQUEST)
            .routeId(RouteID.BAD_REQUEST)
            .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("400"))
            .to(Endpoint.DIRECT_SET_EMPTY_RESPONSELIST);

        // Handle the MismatchedInputException
        from(Endpoint.DIRECT_MISMATCHED_INPUT_FILE)
            .routeId(RouteID.MISMATCHED_INPUT_FILE)
            .log("Cannot deserialize the file content to Person[] object: ${in.body}");

        // Set the ResponseListDTO as body with a
        // list of ssn into the error list.
        from(Endpoint.DIRECT_SET_EMPTY_RESPONSELIST)
            .routeId(RouteID.SET_EMPTY_RESPONSELIST)
            .setBody(emptyResponseListSupplier);
    }
}
