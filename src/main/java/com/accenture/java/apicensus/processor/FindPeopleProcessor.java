package com.accenture.java.apicensus.processor;

import com.accenture.java.apicensus.entity.Country;
import com.accenture.java.apicensus.entity.Endpoint;
import com.accenture.java.apicensus.entity.Person;
import com.accenture.java.apicensus.entity.dto.FindOnePersonDTO;
import com.accenture.java.apicensus.entity.dto.ResponseListDTO;
import com.accenture.java.apicensus.mapper.PersonMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * The processor responsible to
 * search the requested people.
 *
 * @author Gian F. S.
 */
@Component
public class FindPeopleProcessor implements Processor {

    @Autowired
    private PersonMapper personMapper;

    @Value("${camel.endpoint.mongodb.people}")
    private String personMongoDBEndpoint;

    /**
     * Process the find person request and sets the
     * out body with a response and status code.
     * <br><br>
     * First of all gets the request body and country value from
     * the header, and validates if them aren't null.<br>
     * If the country is null but the body aren't, the content
     * will be added to the error list.<br>
     * If every was OK, iterate the array and validates if each
     * value is numeric. After passing the validation, will search
     * the person, if the person is found, it will be added to
     * the success list, otherwise it ssn will be added to errors
     * the list.<br>
     * Finally the outcome will be set to the out body with the
     * appropriate status code.
     *
     * @param exchange the exchange
     * @throws Exception the exception
     *
     * @see Exchange
     * @see ResponseListDTO
     * @see Objects#nonNull(Object)
     * @see FindOnePersonDTO
     * @see StringUtils#isNumeric(CharSequence)
     * @see #findPerson(Integer, String, Exchange)
     * @see Optional
     * @see ResponseListDTO#getStatusCode()
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        ResponseListDTO responseListDTO = new ResponseListDTO();

        Object[] ssnList = exchange.getIn().getBody(Object[].class);
        if (Objects.nonNull(ssnList)) {
            Country country = exchange.getIn().getHeader("country", Country.class);
            if (Objects.nonNull(country)) {
                for (Object ssn : ssnList) {
                    String ssnString = String.valueOf(ssn);

                    if (StringUtils.isNumeric(ssnString)) {
                        Optional<Person> optionalPerson = findPerson(Integer.parseInt(ssnString), country.name(), exchange);

                        if (optionalPerson.isPresent()) {
                            responseListDTO.addSuccess(personMapper.toDTO(optionalPerson.get()));
                        } else {
                            responseListDTO.addError(ssnString);
                        }
                    } else {
                        responseListDTO.addError(ssn);
                    }
                }
            } else {
                responseListDTO.addErrors(ssnList);
            }
        }

        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, responseListDTO.getStatusCode());
        exchange.getOut().setBody(responseListDTO);
    }

    /**
     * Returns an optional with the person.
     * <br><br>
     * It creates a ProducerTemplate depending on
     * exchange param and calls the camel endpoint
     * responsible for the search.
     *
     * @param ssn the person ssn
     * @param country the person nationality
     * @param exchange the exchange, used to create
     *                 the ProducerTemplate
     *
     * @return an optional with the person.
     *
     * @see ProducerTemplate
     * @see ProducerTemplate#requestBody(org.apache.camel.Endpoint, Object, Class)
     * @see Optional
     */
    private Optional<Person> findPerson(Integer ssn, String country, Exchange exchange) {
        FindOnePersonDTO findOnePersonDTO = new FindOnePersonDTO(ssn, country);

        // creates the ProducerTemplate to call a Camel endpoint
        ProducerTemplate producerTemplate = exchange.getContext().createProducerTemplate();
        producerTemplate.setDefaultEndpointUri(Endpoint.DIRECT_DEFAULT_ENDPOINT.endpoint());

        // calls the camel endpoint
        return producerTemplate.requestBody(
            Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY.endpoint(),
            findOnePersonDTO,
            Optional.class);
    }
}
