package com.accenture.java.apicensus.processor;

import com.accenture.java.apicensus.entity.Country;
import com.accenture.java.apicensus.entity.Person;
import com.accenture.java.apicensus.entity.dto.FindOnePersonDTO;
import com.accenture.java.apicensus.entity.dto.PersonDTO;
import com.accenture.java.apicensus.entity.dto.ResponseListDTO;
import com.accenture.java.apicensus.mapper.PersonMapper;
import com.accenture.java.apicensus.utils.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The processor responsible to
 * search the requested people.
 *
 * @author Gian F. S.
 */
@Component
public class FindPeopleProcessor implements Processor {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private PersonMapper personMapper;

    /**
     * Process the find person request and sets the
     * out body with a response and status code.
     * <br><br>
     * First of all, find each ssn and returns all
     * available person information.<br>
     * After that, creates the error list with all
     * the ssn that have not processed.<br>
     * Finally, sets each list into the response
     *
     * @param exchange the exchange
     *
     * @see Exchange
     * @see ResponseListDTO
     * @see ResponseListDTO#getStatusCode()
     */
    @Override
    public void process(Exchange exchange) {
        ResponseListDTO responseListDTO = new ResponseListDTO();

        try {
            List<Object> ssnListRequest = exchange.getIn().getMandatoryBody(List.class);

            List<PersonDTO> peopleListSuccess = findPeople(exchange, ssnListRequest);
            List<Object> ssnListError = getErrorList(ssnListRequest, peopleListSuccess);

            responseListDTO.addSuccess(peopleListSuccess);
            responseListDTO.addErrors(ssnListError);
        } catch (Exception e) {
            logger.debug("An exception occurred during body recovery.");
        } finally {
            exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, responseListDTO.getStatusCode());
            exchange.getOut().setBody(responseListDTO);
        }
    }

    /**
     * Finds the person information for each ssn.
     * <br><br>
     * Use the stream method functionality to filter and
     * map the list.<br>
     * During the stream process, a camel endpoint is called to
     * retrieve the person data, using {@link ProducerTemplate}.<br>
     * Finally, each person are collected into a list.
     *
     * @param exchange the exchange.
     * @param ssnListRequest the request list that contains
     *                       each ssn.
     *
     * @return the list of people data.
     */
    private List<PersonDTO> findPeople(Exchange exchange, List<Object> ssnListRequest) {
        String country = exchange.getIn().getHeader("country", Country.class).name();

        ProducerTemplate producerTemplate = exchange.getContext().createProducerTemplate();
        producerTemplate.setDefaultEndpointUri(Endpoint.DIRECT_DEFAULT_ENDPOINT);

        return ssnListRequest.stream()
            .filter(Objects::nonNull)
            .map(Object::toString)
            .filter(StringUtils::isNumeric)
            .map(Integer::parseInt)
            .map(ssn -> FindOnePersonDTO.builder().ssn(ssn).country(country).build())
            .map(findOnePersonDTO -> (Optional<Person>) producerTemplate
                .requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, findOnePersonDTO, Optional.class))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(personMapper::toDTO)
            .collect(Collectors.toList());
    }

    /**
     * Returns the list of wrong ssn.
     * <br><br>
     * The ssnListRequest is the list of requested ssn
     * and peopleListSuccess is the list of people
     * successfully processed.<br>
     * The error list is contained by ssn with {@code null},
     * non numeric and non-existent person values.
     *
     * @param ssnListRequest is the list of requested ssn
     * @param peopleListSuccess is the list of people
     *                          successfully processed
     *
     * @return the list of wrong ssn.
     */
    private List<Object> getErrorList(List<Object> ssnListRequest, List<PersonDTO> peopleListSuccess) {
        return ssnListRequest.stream()
            .filter(ssn ->
                Objects.isNull(ssn) ||
                !StringUtils.isNumeric(ssn.toString()) ||
                peopleListSuccess.parallelStream()
                    .map(PersonDTO::getSsn)
                    .noneMatch(ssnPeople -> ssnPeople.equals(Integer.parseInt(ssn.toString()))))
            .collect(Collectors.toList());
    }
}
