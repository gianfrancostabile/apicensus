package com.accenture.java.apicensus.resource;

import com.accenture.java.apicensus.entity.ResponseList;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ExceptionResource extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:internalServerError")
            .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("500"))
            .to("direct:setSameBody");

        from("direct:badRequest")
            .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("400"))
            .to("direct:setSameBody");

        from("direct:setSameBody")
            .setBody(exchange -> {
                ResponseList responseList = new ResponseList();
                responseList.addError(exchange.getIn().getBody(Object[].class));
                return responseList;
            });

    }
}
