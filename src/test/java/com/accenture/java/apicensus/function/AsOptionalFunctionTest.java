package com.accenture.java.apicensus.function;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
public class AsOptionalFunctionTest {

    private AsOptionalFunction asOptionalFunction;
    private Exchange exchange;

    @Before
    public void setUp() {
        this.asOptionalFunction = new AsOptionalFunction();

        CamelContext camelContext = new DefaultCamelContext();
        this.exchange = new DefaultExchange(camelContext);
    }

    @Test
    public void apply_NotEmptyOptional_IfExchangeNotNullAndExchangeBodyNotNull() {
        this.exchange.getIn()
            .setBody("The optional body");

        assertEquals("The optional body", this.asOptionalFunction.apply(this.exchange)
            .get());
    }

    @Test
    public void apply_EmptyOptional_IfExchangeIsNull() {
        assertFalse(this.asOptionalFunction.apply(null)
            .isPresent());
    }

    @Test
    public void apply_EmptyOptional_IfExchangeNotNullAndExchangeBodyIsEmpty() {
        assertFalse(this.asOptionalFunction.apply(this.exchange)
            .isPresent());
    }

    @Test
    public void apply_EmptyOptional_IfExchangeNotNullAndExchangeBodyIsNull() {
        assertFalse(this.asOptionalFunction.apply(null)
            .isPresent());
    }
}
