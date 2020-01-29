package com.accenture.java.apicensus.controller;

import com.accenture.java.apicensus.entity.UserCredentials;
import com.accenture.java.apicensus.utils.Endpoint;
import com.accenture.java.apicensus.utils.RouteID;
import com.accenture.java.apicensus.utils.Tag;
import org.apache.camel.Exchange;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

@Controller
public class AuthenticationController extends MainController {

    @Override
    public void configure() throws Exception {
        super.configure();

        rest()
            .post("/authenticate")
                .tag(Tag.POST.name())
                .description("Returns the authentication token using the user credentials")
                .responseMessage()
                    .code(200).responseModel(String.class).message("Successfully authenticated")
                .endResponseMessage()
                .responseMessage()
                    .code(400).message("Fail to authenticate")
                .endResponseMessage()
                .type(UserCredentials.class)
                .consumes(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .route().routeId(RouteID.AUTHENTICATE_POST.id())
                    .doTry()
                        .to(Endpoint.DIRECT_AUTHENTICATE)
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("200"))
                    .doCatch(Exception.class)
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("400"))
                        .setBody(constant(null))
                    .endDoTry()
            .endRest();
    }
}
