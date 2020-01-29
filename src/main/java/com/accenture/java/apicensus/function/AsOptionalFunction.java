package com.accenture.java.apicensus.function;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Gian F. S.
 */
@Component
public class AsOptionalFunction implements Function<Exchange, Optional> {

    /**
     * Converts the exchange body to optional.
     *
     * @param exchange the exchange.
     * @return the optional with the exchange body.
     * If exchange is {@code null}, returns an
     * empty optional
     * @see Optional
     * @see Exchange
     */
    @Override
    public Optional apply(Exchange exchange) {
        return Objects.nonNull(exchange) ?
            Optional.ofNullable(exchange.getIn()
                .getBody()) :
            Optional.empty();
    }
}
