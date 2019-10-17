package com.accenture.java.apicensus.resource;

import com.accenture.java.apicensus.entity.Country;
import com.accenture.java.apicensus.entity.Person;
import com.accenture.java.apicensus.entity.dto.FindOnePersonDTO;
import com.accenture.java.apicensus.utils.Endpoint;
import com.mongodb.MongoClient;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(CamelSpringBootRunner.class)
public class DatabaseResourceTest extends CamelTestSupport {

    @Override
    protected RoutesBuilder createRouteBuilder() {
        return new DatabaseResource();
    }

    @Override
    protected JndiRegistry createRegistry() throws Exception {
        JndiRegistry registry = super.createRegistry();
        registry.bind("mongoDBBean", new MongoClient("localhost", 27017));
        return registry;
    }

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    @Test
    public void findOneBySsnAndCountry_sendExistingPersonId_returnOptionalWithPerson() throws Exception {
        mock_Mongodb_Person_FindOneBy_SsnAndCountry_To_Success();

        context().start();

        Optional optionalPerson = template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY,
            FindOnePersonDTO.builder().ssn(11111111).country(Country.UY.name()).build(), Optional.class);

        assertTrue("Person founded", optionalPerson.isPresent());

        Person expectedPerson = Person.builder().ssn(11111111).name("Luis").surname("Malo").bornDate("17-04-1986")
            .country(Country.UY.name()).genre("Male").build();

        assertEquals("Both persons are equals", expectedPerson, optionalPerson.get());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_sendNonExistentPersonId_returnEmptyOptional() throws Exception {
        mock_Mongodb_Person_FindOneBy_SsnAndCountry_To_Fail();

        context().start();

        Optional optionalPerson = template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY,
            FindOnePersonDTO.builder().ssn(10101010).country(Country.AR.name()).build(), Optional.class);

        assertFalse("Person not founded", optionalPerson.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_sendFindOneDTOWithSsnNull_returnEmptyOptional() throws Exception {
        context().start();

        Optional optionalPerson = template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY,
            FindOnePersonDTO.builder().ssn(null).country(Country.AR.name()).build(), Optional.class);

        assertFalse("Person not founded", optionalPerson.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_sendFindOneDTOWithCountryNull_returnEmptyOptional() throws Exception {
        context().start();

        Optional optionalPerson = template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY,
            FindOnePersonDTO.builder().ssn(11111111).country(null).build(), Optional.class);

        assertFalse("Person not founded", optionalPerson.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_sendFindOneDTOWithSsnAndCountryNull_returnEmptyOptional() throws Exception {
        context().start();

        Optional optionalPerson = template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY,
            FindOnePersonDTO.builder().ssn(null).country(null).build(), Optional.class);

        assertFalse("Person not founded", optionalPerson.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_sendNull_returnEmptyOptional() throws Exception {
        context().start();

        Optional optionalPerson =
            template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, null, Optional.class);

        assertFalse("Person not founded", optionalPerson.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_sendByte_returnOptionalEmpty() throws Exception {
        context().start();

        Optional optionalPersonByte =
            template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, (byte) 10110000, Optional.class);

        assertFalse("Unexpected class (Byte), person not founded", optionalPersonByte.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_sendShort_returnOptionalEmpty() throws Exception {
        context().start();

        Optional optionalPersonShort =
            template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, (short) 2, Optional.class);

        assertFalse("Unexpected class (Short), person not founded", optionalPersonShort.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_sendInteger_returnOptionalEmpty() throws Exception {
        context().start();

        Optional optionalPersonInteger =
            template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, 10101010, Optional.class);

        assertFalse("Unexpected class (Integer), person not founded", optionalPersonInteger.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_sendLong_returnOptionalEmpty() throws Exception {
        context().start();

        Optional optionalPersonLong =
            template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, 10101010L, Optional.class);

        assertFalse("Unexpected class (Long), person not founded", optionalPersonLong.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_sendFloat_returnOptionalEmpty() throws Exception {
        context().start();

        Optional optionalPersonFloat =
            template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, 10101010F, Optional.class);

        assertFalse("Unexpected class (Float), person not founded", optionalPersonFloat.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_sendDouble_returnOptionalEmpty() throws Exception {
        context().start();

        Optional optionalPersonDouble =
            template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, 10101010D, Optional.class);

        assertFalse("Unexpected class (Double), person not founded", optionalPersonDouble.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_sendChar_returnOptionalEmpty() throws Exception {
        context().start();

        Optional optionalPersonChar =
            template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, 's', Optional.class);

        assertFalse("Unexpected class (Char), person not founded", optionalPersonChar.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_sendString_returnOptionalEmpty() throws Exception {
        context().start();

        Optional optionalPersonString =
            template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, "10101010", Optional.class);

        assertFalse("Unexpected class (String), person not founded", optionalPersonString.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_sendBooleanTrue_returnOptionalEmpty() throws Exception {
        context().start();

        Optional optionalPersonBooleanTrue =
            template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, true, Optional.class);

        assertFalse("Unexpected class (Boolean True), person not founded", optionalPersonBooleanTrue.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_sendBooleanFalse_returnOptionalEmpty() throws Exception {
        context().start();

        Optional optionalPersonBooleanFalse =
            template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, false, Optional.class);

        assertFalse("Unexpected class (Boolean False), person not founded", optionalPersonBooleanFalse.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_sendEntity_returnOptionalEmpty() throws Exception {
        context().start();

        Optional optionalPersonEntity = template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY,
            Person.builder().name("Luis").surname("Gonzalez").build(), Optional.class);

        assertFalse("Unexpected class (Entity), person not founded", optionalPersonEntity.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_sendEntityWithSameFields_returnOptionalEmpty() throws Exception {
        context().start();

        Optional optionalPersonEntitySameFields = template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY,
            Person.builder().ssn(11111111).country(Country.UY.name()).build(), Optional.class);

        assertFalse("Unexpected class (Entity) with same fields, person not founded",
            optionalPersonEntitySameFields.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_sendFindOnePersonDTOList_returnOptionalEmpty() throws Exception {
        context().start();

        List<FindOnePersonDTO> findOnePersonDTOList = new ArrayList<>();
        findOnePersonDTOList.add(FindOnePersonDTO.builder().ssn(10101010).country(Country.BR.name()).build());
        findOnePersonDTOList.add(FindOnePersonDTO.builder().ssn(10101011).country(Country.UY.name()).build());
        findOnePersonDTOList.add(FindOnePersonDTO.builder().ssn(11111111).country(Country.UY.name()).build());

        Optional optionalPersonList =
            template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, findOnePersonDTOList, Optional.class);

        assertFalse("Unexpected class (List), person not founded", optionalPersonList.isPresent());

        context().stop();
    }

    private void mock_Mongodb_Person_FindOneBy_SsnAndCountry_To_Success() throws Exception {
        AdviceWithRouteBuilder adviceWithRouteBuilder = new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                // intercept mongodb endpoint to mock:findOneByQuerySuccess and do something else
                interceptSendToEndpoint(Endpoint.MONGODB_PERSON_FINDONEBY_SSNANDCOUNTRY).skipSendToOriginalEndpoint()
                    .to(Endpoint.DIRECT_FINDONEBYQUERY_SUCCESS);
            }
        };
        context().getRouteDefinitions().get(0).adviceWith(context(), adviceWithRouteBuilder);
    }

    private void mock_Mongodb_Person_FindOneBy_SsnAndCountry_To_Fail() throws Exception {
        AdviceWithRouteBuilder adviceWithRouteBuilder = new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                // intercept mongodb endpoint to mock:findOneByQuerySuccess and do something else
                interceptSendToEndpoint(Endpoint.MONGODB_PERSON_FINDONEBY_SSNANDCOUNTRY).skipSendToOriginalEndpoint()
                    .to(Endpoint.DIRECT_FINDONEBYQUERY_FAIL);
            }
        };
        context().getRouteDefinitions().get(0).adviceWith(context(), adviceWithRouteBuilder);
    }
}
