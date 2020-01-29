package com.accenture.java.apicensus.function;

import com.accenture.java.apicensus.entity.Country;
import com.mongodb.BasicDBObject;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * @author Gian F. S.
 */
@Component
public class BasicDBObjectAsPersonSupplier implements Supplier<BasicDBObject> {

    /**
     * Returns a DBObject instance with
     * the person data.
     *
     * @return basicDBObject instance.
     */
    @Override
    public BasicDBObject get() {
        return new BasicDBObject().append("ssn", 11111111L)
            .append("name", "Luis")
            .append("surname", "Malo")
            .append("bornDate", "17-04-1986")
            .append("country", Country.UY.name())
            .append("genre", "Male");
    }
}
