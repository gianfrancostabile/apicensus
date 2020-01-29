package com.accenture.java.apicensus.resource;

import com.accenture.java.apicensus.exception.NullBodyException;
import com.accenture.java.apicensus.function.AuthenticationFunction;
import com.accenture.java.apicensus.jwt.JwtToken;
import com.accenture.java.apicensus.service.JwtUserDetailsService;
import com.accenture.java.apicensus.utils.Endpoint;
import com.accenture.java.apicensus.utils.RouteID;
import org.apache.camel.util.toolbox.AggregationStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @author Gian F. S.
 */
@Component
public class AuthenticationResource extends ExceptionCatcherResource {

    @Autowired
    private AuthenticationFunction authenticationFunction;

    @Override
    public void configure() throws Exception {
        super.configure();

        from(Endpoint.DIRECT_AUTHENTICATE).routeId(RouteID.AUTHENTICATE.id())
            .to(Endpoint.BEAN_VALIDATOR_DEFAULT_GROUP)
            .enrich(Endpoint.DIRECT_AUTHENTICATE_WITH_MANAGER, AggregationStrategies.useOriginal())
            .bean(JwtUserDetailsService.class, "loadUserByUsername(${body.username})")
            .bean(JwtToken.class, "generateToken(${body.username})");


        from(Endpoint.DIRECT_AUTHENTICATE_WITH_MANAGER).routeId(RouteID.AUTHENTICATION_MANAGER.id())
            .setBody(authenticationFunction)
            .choice()
                .when(bodyAs(Authentication.class).isNull())
                    .throwException(new NullBodyException())
            .end();
    }
}
