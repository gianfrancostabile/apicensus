package com.accenture.java.apicensus.processor;

import com.accenture.java.apicensus.entity.Person;
import com.accenture.java.apicensus.entity.dto.FindOnePersonDTO;
import com.accenture.java.apicensus.entity.dto.PersonDTO;
import com.accenture.java.apicensus.mapper.PersonMapper;
import com.accenture.java.apicensus.entity.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * The processor responsible to insert
 * the requested person into the database.
 *
 * @author Gian F. S.
 */
@Component
public class InsertPersonProcessor implements Processor {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private PersonMapper personMapper;

    /**
     * Process the save person request.
     * <br><br>
     * First of all gets the person from the exchange body
     * and validates if it isn't null.<br>
     * If the body isn't null, creates a ProducesTemplate
     * instance to call camel endpoints. After that searches
     * for an existing person into the database. If there is
     * none, the person will be saved, otherwise an error
     * message will be logged.
     *
     * @param exchange the exchange
     * @throws Exception the exception
     *
     * @see Exchange
     * @see PersonDTO
     * @see Objects#nonNull(Object)
     * @see FindOnePersonDTO
     * @see ProducerTemplate
     * @see ProducerTemplate#requestBody(org.apache.camel.Endpoint, Object, Class)
     * @see Optional
     * @see ProducerTemplate#sendBody(org.apache.camel.Endpoint, Object)
     * @see Logger#error(Object)
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        PersonDTO personDTO = exchange.getIn().getBody(PersonDTO.class);
        if (Objects.nonNull(personDTO)) {
            FindOnePersonDTO findOnePersonDTO = new FindOnePersonDTO(
                personDTO.getSsn(),
                personDTO.getCountry().name());

            ProducerTemplate producerTemplate = exchange.getContext().createProducerTemplate();
            producerTemplate.setDefaultEndpointUri(Endpoint.DIRECT_DEFAULT_ENDPOINT.endpoint());

            Optional<Person> optionalPerson = producerTemplate.requestBody(
                Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY.endpoint(),
                findOnePersonDTO,
                Optional.class);

            if (!optionalPerson.isPresent()) {
                Person person = personMapper.toEntity(personDTO);
                producerTemplate.sendBody(Endpoint.DIRECT_INSERT_DB_PERSON.endpoint(), person);
            } else {
                logger.error("A person with SSN[" + findOnePersonDTO.getSsn() +
                    "] and COUNTRY[" + findOnePersonDTO.getCountry() + "] already exists");
            }
        }
    }
}
