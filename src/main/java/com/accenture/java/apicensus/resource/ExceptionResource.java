package com.accenture.java.apicensus.resource;

import com.accenture.java.apicensus.entity.Endpoint;
import com.accenture.java.apicensus.entity.dto.ResponseListDTO;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Creates all camel endpoints to set the
 * body after an exception occurs.
 *
 * @author Gian F. S.
 */
@Component
public class ExceptionResource extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // Handle the INTERNAL SERVER ERROR
        from(Endpoint.DIRECT_INTERNAL_SERVER_ERROR.endpoint())
            .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("500"))
            .to(Endpoint.DIRECT_SET_SAME_BODY.endpoint());

        // Handle the BAD REQUEST
        from(Endpoint.DIRECT_BAD_REQUEST.endpoint())
            .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("400"))
            .to(Endpoint.DIRECT_SET_SAME_BODY.endpoint());

        // Set the ResponseListDTO as body with a
        // list of ssn into the error list.
        from(Endpoint.DIRECT_SET_SAME_BODY.endpoint())
            .setBody(exchange -> {
                ResponseListDTO responseListDTO = new ResponseListDTO();
                responseListDTO.addError(exchange.getIn().getBody(Object[].class));
                return responseListDTO;
            });

    }
}
