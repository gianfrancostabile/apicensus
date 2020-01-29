package com.accenture.java.apicensus.controller;

import com.accenture.java.apicensus.entity.UserCredentials;
import com.accenture.java.apicensus.exception.UserInsertionException;
import com.accenture.java.apicensus.utils.Endpoint;
import com.accenture.java.apicensus.utils.RouteID;
import com.accenture.java.apicensus.utils.Tag;
import org.apache.camel.Exchange;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

/**
 * @author Gian F. S.
 */
@Controller
public class UserCredentialsController extends MainController {

    @Override
    public void configure() throws Exception {
        super.configure();

        rest()
            .post("/user")
                .tag(Tag.POST.name())
                .description("Register a new user into the system")
                .responseMessage()
                    .code(201).message("The user was successfully created.")
                .endResponseMessage()
                .responseMessage()
                    .code(400).message("The user was not created.")
                .endResponseMessage()
                .type(UserCredentials.class)
                .consumes(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .route().routeId(RouteID.USER_POST.id())
                    .doTry()
                        .to(Endpoint.DIRECT_INSERT_USER)
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("201"))
                        .log("User successfully inserted")
                    .doCatch(UserInsertionException.class)
                        .log("Error user insertion")
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("400"))
                    .doFinally()
                        .setBody(constant(null))
                    .endDoTry()
            .endRest();
    }
}
