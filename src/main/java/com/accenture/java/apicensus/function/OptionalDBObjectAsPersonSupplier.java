package com.accenture.java.apicensus.function;

import com.accenture.java.apicensus.entity.Country;
import com.mongodb.BasicDBObject;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author Gian F. S.
 */
@Component
public class OptionalDBObjectAsPersonSupplier implements Supplier<Optional> {

    /**
     * Returns an optional with a DBObject
     * instance with the person data.
     *
     * @return optional with DBObject instance.
     */
    @Override
    public Optional get() {
        return Optional.of(new BasicDBObject().append("ssn", 11111111).append("name", "Luis").append("surname", "Malo")
            .append("bornDate", "17-04-1986").append("country", Country.UY.name()).append("genre", "Male"));
    }
}
