package com.accenture.java.apicensus.processor;

import com.mongodb.BasicDBObjectBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.mongodb.MongoDbConstants;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * The processor responsible to remove mongodb _id field
 *
 * @author Gian F. S.
 */
@Component
public class RemoveMongoIDProcessor implements Processor {

    /**
     * Removes the mongodb _id field.
     * <br><br>
     * Adding the field in the header, allows mongo not
     * to return it when searching the documents.
     * <br>
     * Used it when you want to retrieve
     * data from any collection and you do not
     * want the _id field.
     *
     * @param exchange the camel exchange
     * @throws Exception the exception to catch
     * @see Exchange
     * @see Objects#nonNull(Object)
     * @see MongoDbConstants
     * @see BasicDBObjectBuilder
     * @see com.mongodb.BasicDBObject
     */
    @Override
    public void process(Exchange exchange) {
        if (Objects.nonNull(exchange)) {
            exchange.getIn()
                .setHeader(MongoDbConstants.FIELDS_FILTER, BasicDBObjectBuilder.start()
                    .add("_id", 0)
                    .get());
        }
    }
}
