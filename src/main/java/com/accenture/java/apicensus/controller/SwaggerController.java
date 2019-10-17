package com.accenture.java.apicensus.controller;

import com.accenture.java.apicensus.utils.Endpoint;
import com.accenture.java.apicensus.utils.RouteID;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Controller;

@Controller
public class SwaggerController extends MainController {

    @Override
    public void configure() throws Exception {
        /**
         * Redirects to access swagger UI via short URL from "/swagger-ui" to
         * "/webjars/swagger-ui/index.html?url=/census/swagger
         *
         * @return a string with the path to redirect
         * @see Endpoint#REDIRECT_SWAGGER
         */
        from("servlet://swagger-ui")
            .routeId(RouteID.REDIRECT_SWAGGER)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("308"))
                .setHeader("location", simple(Endpoint.REDIRECT_SWAGGER));
    }
}
