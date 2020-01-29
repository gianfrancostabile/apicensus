package com.accenture.java.apicensus.function;

import com.accenture.java.apicensus.entity.Country;
import com.mongodb.BasicDBObject;
import org.junit.Assert;
import org.junit.Test;

public class BasicDBObjectAsPersonSupplierTest {

    @Test
    public void get_BasicDBObject_WithoutArguments() {
        BasicDBObject expectedValue = new BasicDBObject().append("ssn", 11111111L)
            .append("name", "Luis")
            .append("surname", "Malo")
            .append("bornDate", "17-04-1986")
            .append("country", Country.UY.name())
            .append("genre", "Male");

        Assert.assertEquals("BasicDBObject should be equals to expected value", expectedValue,
            new BasicDBObjectAsPersonSupplier().get());
    }
}
