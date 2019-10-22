package com.accenture.java.apicensus.resource;

import com.accenture.java.apicensus.entity.dto.FindOnePersonDTO;
import com.accenture.java.apicensus.function.EmptyOptionalSupplier;
import com.accenture.java.apicensus.function.FindOnePersonBuildRequestFunction;
import com.accenture.java.apicensus.function.FindOnePersonBuildResponseFunction;
import com.accenture.java.apicensus.function.OptionalDBObjectAsPersonSupplier;
import com.accenture.java.apicensus.processor.RemoveMongoIDProcessor;
import com.accenture.java.apicensus.utils.Endpoint;
import com.accenture.java.apicensus.utils.RouteID;
import org.apache.camel.component.bean.validator.BeanValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Creates all camel endpoints related to database
 *
 * @author Gian F. S.
 */
@Component
public class DatabaseResource extends ExceptionCatcherResource {

    @Autowired
    private RemoveMongoIDProcessor removeMongoIDProcessor;

    @Autowired
    private EmptyOptionalSupplier emptyOptionalSupplier;

    @Autowired
    private FindOnePersonBuildRequestFunction findOnePersonBuildRequestFunction;

    @Autowired
    private FindOnePersonBuildResponseFunction findOnePersonBuildResponseFunction;

    @Autowired
    private OptionalDBObjectAsPersonSupplier optionalDBObjectAsPersonSupplier;

    @Override
    public void configure() throws Exception {
        super.configure();

        // Saves the person into the database
        from(Endpoint.DIRECT_INSERT_DB_PERSON)
            .routeId(RouteID.MONGODB_PERSON_INSERT)
            .to(Endpoint.BEAN_VALIDATOR_DEFAULT_GROUP)
            .to(Endpoint.MONGODB_PERSON_INSERT)
            .log("A Person was added. ${in.body}");

        // Search the person by ssn and country
        from(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY)
            .routeId(RouteID.MONGODB_PERSON_FINDONEBY_SSNANDCOUNTRY)
            .choice()
                .when(bodyAs(FindOnePersonDTO.class).isNull())
                    .setBody(emptyOptionalSupplier)
                .otherwise()
                    .doTry()
                        .to(Endpoint.BEAN_VALIDATOR_DEFAULT_GROUP)
                        .process(removeMongoIDProcessor)
                        .setBody(findOnePersonBuildRequestFunction)
                        .to(Endpoint.MONGODB_PERSON_FINDONEBY_SSNANDCOUNTRY)
                        .setBody(findOnePersonBuildResponseFunction)
                    .doCatch(BeanValidationException.class)
                        .log("Some fields are nulls: ssn[${body.ssn}] - country[${body.country}]")
                        .setBody(emptyOptionalSupplier)
                    .endDoTry()
            .end();

        from(Endpoint.DIRECT_FINDONEBYQUERY_SUCCESS)
            .routeId(RouteID.DIRECT_FINDONEBYQUERY_SUCCESS)
            .setBody(optionalDBObjectAsPersonSupplier);

        from(Endpoint.DIRECT_FINDONEBYQUERY_FAIL)
            .routeId(RouteID.DIRECT_FINDONEBYQUERY_FAIL)
            .setBody(emptyOptionalSupplier);
    }
}
