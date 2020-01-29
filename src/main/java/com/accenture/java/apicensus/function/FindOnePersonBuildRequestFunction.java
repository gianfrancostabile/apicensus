package com.accenture.java.apicensus.function;

import com.accenture.java.apicensus.entity.dto.FindOnePersonDTO;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Gian F. S.
 */
@Component
public class FindOnePersonBuildRequestFunction implements Function<Exchange, DBObject> {

    /**
     * Returns an object with ssn and country
     * that mongodb use to filter.
     * <br><br>
     * The class {@link FindOnePersonDTO} is used
     * to get the ssn and country values.
     *
     * @param exchange the exchange
     * @return and object with ssn and country
     * @see DBObject
     * @see Exchange
     * @see BasicDBObjectBuilder
     * @see Objects
     * @see Optional
     * @see FindOnePersonDTO
     */
    @Override
    public DBObject apply(Exchange exchange) {
        DBObject dbObject = BasicDBObjectBuilder.start()
            .get();
        if (Objects.nonNull(exchange)) {
            Optional<FindOnePersonDTO> optionalDTO = Optional.ofNullable(exchange.getIn()
                .getBody(FindOnePersonDTO.class));
            if (optionalDTO.isPresent()) {
                FindOnePersonDTO findOnePersonDTO = optionalDTO.get();
                dbObject = BasicDBObjectBuilder.start()
                    .add("ssn", findOnePersonDTO.getSsn())
                    .add("country", findOnePersonDTO.getCountry())
                    .get();
            }
        }
        return dbObject;
    }
}
