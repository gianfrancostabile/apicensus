package com.accenture.java.apicensus.processor;

import com.accenture.java.apicensus.entity.Person;
import com.accenture.java.apicensus.entity.RequestDTO;
import com.accenture.java.apicensus.service.IPersonService;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class
GetOnePersonProcessor implements Processor {

    @Autowired
    @Qualifier(value = "personFileService")
    private IPersonService iPersonService;

    @Override
    public void process(Exchange exchange) throws Exception {
        RequestDTO requestDTO = exchange.getIn().getBody(RequestDTO.class);

        int statusCode;
        Object objectResponse = null;

        if (requestDTO != null && requestDTO.getSsn() != null
                && requestDTO.getCountry() != null) {

            Optional<Person> optionalPerson = this.iPersonService
                .findById(requestDTO.getSsn(), requestDTO.getCountry());
            if (optionalPerson.isPresent()) {
                statusCode = 200;
                objectResponse = optionalPerson.get();
            } else {
                statusCode = 404;
                objectResponse = requestDTO.getSsn();
            }
        } else {
            statusCode = 400;
        }

        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, statusCode);
        exchange.getOut().setBody(objectResponse);
    }
}
