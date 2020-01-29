package com.accenture.java.apicensus.function;

import com.accenture.java.apicensus.entity.UserCredentials;
import com.accenture.java.apicensus.exception.NullBodyException;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class AuthenticationFunction implements Function<Exchange, Authentication> {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Authentication apply(Exchange exchange) {
        UserCredentials userCredentials = Optional.ofNullable(exchange.getIn()
            .getBody(UserCredentials.class))
            .orElseThrow(NullBodyException::new);
        return authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userCredentials.getUsername(), userCredentials.getPassword()));
    }
}
