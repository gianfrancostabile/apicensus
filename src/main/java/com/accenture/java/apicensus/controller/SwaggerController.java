package com.accenture.java.apicensus.controller;

import com.accenture.java.apicensus.utils.Endpoint;
import com.accenture.java.apicensus.utils.RouteID;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Controller;

/**
 * @author Gian F. S.
 */
@Controller
public class SwaggerController extends MainController {

    /**
     * Redirects to access swagger UI via short URL from "/swagger-ui" to
     * "/webjars/swagger-ui/index.html?url=/census/swagger
     *
     * @see Endpoint#REDIRECT_SWAGGER
     */
    @Override
    public void configure() {
        from("servlet://swagger-ui")
            .routeId(RouteID.REDIRECT_SWAGGER)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("308"))
                .setHeader("location", simple(Endpoint.REDIRECT_SWAGGER));
    }
}
