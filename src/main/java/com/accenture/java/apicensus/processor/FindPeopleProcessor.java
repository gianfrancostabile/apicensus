package com.accenture.java.apicensus.processor;

import com.accenture.java.apicensus.entity.Country;
import com.accenture.java.apicensus.entity.Person;
import com.accenture.java.apicensus.entity.dto.FindOnePersonDTO;
import com.accenture.java.apicensus.entity.dto.PersonDTO;
import com.accenture.java.apicensus.entity.dto.ResponseListDTO;
import com.accenture.java.apicensus.exception.NullArgumentException;
import com.accenture.java.apicensus.exception.NullHeaderException;
import com.accenture.java.apicensus.mapper.PersonMapper;
import com.accenture.java.apicensus.utils.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.InvalidPayloadException;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
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
     * @see Exchange
     * @see Logger
     * @see LogManager
     * @see NullHeaderException
     * @see NullArgumentException
     * @see InvalidPayloadException
     * @see Exception
     * @see ResponseListDTO
     * @see ResponseListDTO#getStatusCode()
     */
    @Override
    public void process(Exchange exchange) {
        Logger logger = LogManager.getLogger(this.getClass());
        ResponseListDTO responseListDTO = new ResponseListDTO();

        try {
            List<Long> ssnListRequest = Arrays.asList(exchange.getIn()
                .getMandatoryBody(Long[].class));

            List<PersonDTO> peopleListSuccess = findPeople(exchange, ssnListRequest);
            List<Long> ssnListError = getErrorList(ssnListRequest, peopleListSuccess);

            responseListDTO.addSuccess(peopleListSuccess);
            responseListDTO.addErrors(ssnListError);
        } catch (NullHeaderException exception) {
            logger.debug("Null header exception during FindPeopleProcessor process.");
        } catch (NullArgumentException exception) {
            logger.debug("Null arguments exception during FindPeopleProcessor process.");
        } catch (InvalidPayloadException exception) {
            logger.debug("Invalid payload exception during FindPeopleProcessor process.");
        } catch (Exception exception) {
            logger.debug("An exception occurred during body recovery.");
        } finally {
            exchange.getOut()
                .setHeader(Exchange.HTTP_RESPONSE_CODE, responseListDTO.getStatusCode());
            exchange.getOut()
                .setBody(responseListDTO);
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
     * @param exchange       the exchange.
     * @param ssnListRequest the request list that contains
     *                       each ssn.
     * @return the list of people data.
     * @see List
     * @see PersonDTO
     * @see Exchange
     * @see NullArgumentException
     * @see NullHeaderException
     * @see Objects#isNull(Object)
     * @see Optional
     * @see ProducerTemplate
     * @see java.util.stream.Stream
     * @see FindOnePersonDTO
     * @see Collectors
     */
    private List<PersonDTO> findPeople(Exchange exchange, List<Long> ssnListRequest) {
        if (Objects.isNull(exchange) || Objects.isNull(exchange.getIn()) || Objects.isNull(ssnListRequest)) {
            throw new NullArgumentException();
        }
        String country = Optional.ofNullable(exchange.getIn()
            .getHeader("country", Country.class))
            .orElseThrow(NullHeaderException::new)
            .name();

        ProducerTemplate producerTemplate = exchange.getContext()
            .createProducerTemplate();
        producerTemplate.setDefaultEndpointUri(Endpoint.DIRECT_DEFAULT_ENDPOINT);

        return ssnListRequest.stream()
            .filter(Objects::nonNull)
            .map(ssn -> FindOnePersonDTO.builder()
                .ssn(ssn)
                .country(country)
                .build())
            .map(findOnePersonDTO -> (Optional<Person>) producerTemplate.requestBody(
                Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, findOnePersonDTO, Optional.class))
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
     * The error list is contained by ssn without
     * person value.
     *
     * @param ssnListRequest    is the list of requested ssn
     * @param peopleListSuccess is the list of people
     *                          successfully processed
     * @return the list of wrong ssn.
     * @see List
     * @see PersonDTO
     * @see NullArgumentException
     * @see Objects#isNull(Object)
     * @see java.util.stream.Stream
     * @see Collectors
     */
    private List<Long> getErrorList(List<Long> ssnListRequest, List<PersonDTO> peopleListSuccess) {
        if (Objects.isNull(ssnListRequest) || Objects.isNull(peopleListSuccess)) {
            throw new NullArgumentException();
        }
        return ssnListRequest.stream()
            .filter(ssnRequest -> peopleListSuccess.parallelStream()
                .map(PersonDTO::getSsn)
                .noneMatch(ssnPeople -> Objects.equals(ssnPeople, ssnRequest)))
            .collect(Collectors.toList());
    }
}
