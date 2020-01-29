package com.accenture.java.apicensus.processor;

import com.accenture.java.apicensus.entity.UserCredentials;
import org.apache.camel.Exchange;
import org.apache.camel.InvalidPayloadException;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Class that encode the user credentials password.
 *
 * @author Gian F. S.
 */
@Component
public class EncodePasswordProcessor implements Processor {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Encodes the user credentials password with
     * an encrypt password encoder.
     *
     * @param exchange the exchange with the user credentials.
     * @throws InvalidPayloadException if body is null or is not an
     *                                 UserCredentials instance.
     */
    @Override
    public void process(Exchange exchange) throws InvalidPayloadException {
        UserCredentials userCredentials = exchange.getIn()
            .getMandatoryBody(UserCredentials.class);
        String encodedPassword = bCryptPasswordEncoder.encode(userCredentials.getPassword());
        userCredentials.setPassword(encodedPassword);
        exchange.getOut().setBody(userCredentials);
    }
}
