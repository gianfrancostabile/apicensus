package com.accenture.java.apicensus.resource;

import com.accenture.java.apicensus.entity.dto.ResponseListDTO;
import com.accenture.java.apicensus.utils.Endpoint;
import com.accenture.java.apicensus.utils.RouteID;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

/**
 * Creates all camel endpoints to set the
 * body after an exception occurs.
 *
 * @author Gian F. S.
 */
@Component
public class ExceptionHandlerResource extends ExceptionCatcherResource {

    @Override
    public void configure() throws Exception {
        super.configure();

        // Handle the INTERNAL SERVER ERROR
        from(Endpoint.DIRECT_INTERNAL_SERVER_ERROR).routeId(RouteID.INTERNAL_SERVER_ERROR)
            .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("500")).to(Endpoint.DIRECT_SET_SAME_BODY);

        // Handle the BAD REQUEST
        from(Endpoint.DIRECT_BAD_REQUEST).routeId(RouteID.BAD_REQUEST)
            .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("400")).to(Endpoint.DIRECT_SET_SAME_BODY);

        // Handle the MismatchedInputException
        from(Endpoint.DIRECT_MISMATCHED_INPUT_FILE).routeId(RouteID.MISMATCHED_INPUT_FILE)
            .log("Cannot deserialize the file content to Person[] object: ${in.body}");

        // Set the ResponseListDTO as body with a
        // list of ssn into the error list.
        from(Endpoint.DIRECT_SET_SAME_BODY).routeId(RouteID.SET_SAME_BODY)
            .setBody(exchange -> {
                ResponseListDTO responseListDTO = new ResponseListDTO();
                responseListDTO.addError(exchange.getIn().getBody(Object[].class));
                return responseListDTO;
            });

    }
}
