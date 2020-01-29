package com.accenture.java.apicensus.resource;

import com.accenture.java.apicensus.entity.Person;
import com.accenture.java.apicensus.entity.UserCredentials;
import com.accenture.java.apicensus.entity.dto.FindOnePersonDTO;
import com.accenture.java.apicensus.exception.InvalidPersonFieldException;
import com.accenture.java.apicensus.exception.NullBodyException;
import com.accenture.java.apicensus.exception.PersonInsertionException;
import com.accenture.java.apicensus.exception.UnexpectedException;
import com.accenture.java.apicensus.exception.UserInsertionException;
import com.accenture.java.apicensus.function.AsOptionalFunction;
import com.accenture.java.apicensus.function.BasicDBObjectAsPersonSupplier;
import com.accenture.java.apicensus.function.FindOnePersonBuildRequestFunction;
import com.accenture.java.apicensus.function.FindOnePersonBuildResponseFunction;
import com.accenture.java.apicensus.processor.EncodePasswordProcessor;
import com.accenture.java.apicensus.processor.RemoveMongoIDProcessor;
import com.accenture.java.apicensus.utils.Endpoint;
import com.accenture.java.apicensus.utils.RouteID;
import com.mongodb.MongoException;
import org.apache.camel.component.bean.validator.BeanValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Creates all camel endpoints related to database
 *
 * @author Gian F. S.
 */
@Component
public class DatabaseResource extends ExceptionCatcherResource {

    @Autowired
    private AsOptionalFunction asOptionalFunction;

    @Autowired
    private FindOnePersonBuildRequestFunction findOnePersonBuildRequestFunction;

    @Autowired
    private FindOnePersonBuildResponseFunction findOnePersonBuildResponseFunction;

    @Autowired
    private BasicDBObjectAsPersonSupplier basicDBObjectAsPersonSupplier;

    @Autowired
    private RemoveMongoIDProcessor removeMongoIDProcessor;

    @Autowired
    private EncodePasswordProcessor encodePasswordProcessor;

    @Override
    public void configure() throws Exception {
        super.configure();

        // Saves the person into the database
        from(Endpoint.DIRECT_INSERT_DB_PERSON)
            .routeId(RouteID.MONGODB_PERSON_INSERT.id())
            .choice()
                .when(bodyAs(Person.class).isNull())
                    .throwException(new NullBodyException())
                .otherwise()
                    .doTry()
                        .to(Endpoint.BEAN_VALIDATOR_DEFAULT_GROUP)
                        .to(Endpoint.MONGODB_PERSON_INSERT)
                        .log("A new person was added")
                    .doCatch(BeanValidationException.class)
                        .throwException(new InvalidPersonFieldException("The person entity has invalid fields"))
                    .doCatch(MongoException.class)
                        .throwException(new PersonInsertionException("An error occur during person insertion"))
                    .doCatch(Exception.class)
                        .throwException(new UnexpectedException())
                    .endDoTry()
            .endChoice();

        // Search the person by ssn and country
        from(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY)
            .routeId(RouteID.MONGODB_PERSON_FINDONEBY_SSNANDCOUNTRY.id())
            .choice()
                .when(bodyAs(FindOnePersonDTO.class).isNull())
                    .setBody(Optional::empty)
                .otherwise()
                    .doTry()
                        .to(Endpoint.BEAN_VALIDATOR_DEFAULT_GROUP)
                        .process(removeMongoIDProcessor)
                        .setBody(findOnePersonBuildRequestFunction)
                        .to(Endpoint.MONGODB_PERSON_FINDONEBY)
                        .setBody(asOptionalFunction)
                        .setBody(findOnePersonBuildResponseFunction)
                    .doCatch(BeanValidationException.class)
                        .log("Some fields are nulls: ssn[${body.ssn}] - country[${body.country}]")
                        .setBody(Optional::empty)
                    .endDoTry()
            .endChoice();

        // creates a new user
        from(Endpoint.DIRECT_INSERT_USER)
            .routeId(RouteID.INSERT_USER.id())
            .doTry()
                .to(Endpoint.BEAN_VALIDATOR_DEFAULT_GROUP)
                .choice()
                    .when(bodyAs(UserCredentials.class).isNull())
                        .throwException(new NullBodyException())
                    .otherwise()
                        .process(encodePasswordProcessor)
                        .to(Endpoint.MONGODB_USER_INSERT)
                .endChoice()
            .endDoTry()
            .doCatch(Exception.class)
                .throwException(new UserInsertionException())
            .endDoTry();
    }
}
