package com.accenture.java.apicensus.processor;

import com.accenture.java.apicensus.entity.Country;
import com.accenture.java.apicensus.entity.Person;
import com.accenture.java.apicensus.entity.ResponseList;
import com.accenture.java.apicensus.service.IPersonService;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetPeopleProcessor implements Processor {

    @Autowired
    @Qualifier(value = "personService")
    private IPersonService iPersonService;

    @Override
    public void process(Exchange exchange) throws Exception {
        Country country = exchange.getIn().getHeader("APICensusCountry", Country.class);
        Object[] ssnList = exchange.getIn().getBody(Object[].class);

        ResponseList responseList = new ResponseList();

        if (country != null && ssnList != null) {
            for (Object ssn : ssnList) {
                String ssnValue = String.valueOf(ssn);
                if (StringUtils.isNumeric(ssnValue)) {
                    Optional<Person> optionalPerson = iPersonService.findOneBySsnAndCountry(Integer.parseInt(ssnValue), country);
                    if (optionalPerson.isPresent()) {
                        responseList.addSuccess(optionalPerson.get());
                    } else {
                        responseList.addError(ssnValue);
                    }
                } else {
                    responseList.addError(ssn);
                }
            }
        } else if (ssnList != null) {
            responseList.addErrors(ssnList);
        }

        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, responseList.getStatusCode());
        exchange.getOut().setBody(responseList);
    }
}
