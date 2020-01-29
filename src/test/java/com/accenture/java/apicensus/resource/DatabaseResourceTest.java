package com.accenture.java.apicensus.resource;

import com.accenture.java.apicensus.entity.Country;
import com.accenture.java.apicensus.entity.Person;
import com.accenture.java.apicensus.entity.dto.FindOnePersonDTO;
import com.accenture.java.apicensus.exception.InvalidPersonFieldException;
import com.accenture.java.apicensus.exception.NullBodyException;
import com.accenture.java.apicensus.exception.PersonInsertionException;
import com.accenture.java.apicensus.function.AsOptionalFunction;
import com.accenture.java.apicensus.function.BasicDBObjectAsPersonSupplier;
import com.accenture.java.apicensus.function.FindOnePersonBuildRequestFunction;
import com.accenture.java.apicensus.function.FindOnePersonBuildResponseFunction;
import com.accenture.java.apicensus.processor.EncodePasswordProcessor;
import com.accenture.java.apicensus.processor.RemoveMongoIDProcessor;
import com.accenture.java.apicensus.utils.Endpoint;
import com.accenture.java.apicensus.utils.RouteID;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@RunWith(CamelSpringBootRunner.class)
public class DatabaseResourceTest extends CamelTestSupport {

    @InjectMocks
    private DatabaseResource databaseResource;

    @Mock
    private AsOptionalFunction asOptionalFunction;

    @Mock
    private FindOnePersonBuildRequestFunction findOnePersonBuildRequestFunction;

    @Mock
    private FindOnePersonBuildResponseFunction findOnePersonBuildResponseFunction;

    @Mock
    private BasicDBObjectAsPersonSupplier basicDBObjectAsPersonSupplier;

    @Mock
    private RemoveMongoIDProcessor removeMongoIDProcessor;

    @Mock
    private EncodePasswordProcessor encodePasswordProcessor;

    @Override
    protected RoutesBuilder createRouteBuilder() {
        return databaseResource;
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
    public void findOneBySsnAndCountry_OptionalWithPerson_IfBodyIsFindOnePersonDTOAndNotNullAndBeanValidAndRequestAndMappingSuccess()
        throws Exception {
        mock_Mongodb_Person_FindOneBy_SsnAndCountry_To_Success();

        Long ssn = 11111111L;
        String country = Country.UY.name();
        String name = "Luis";
        String surname = "Malo";
        String bornDate = "17-04-1986";
        String genre = "Male";

        DBObject dummyDBObject = BasicDBObjectBuilder.start()
            .add("ssn", ssn)
            .add("country", country)
            .get();

        BasicDBObject dummyBasicDBObject = new BasicDBObject().append("ssn", ssn)
            .append("name", name)
            .append("surname", surname)
            .append("bornDate", bornDate)
            .append("country", country)
            .append("genre", genre);

        Person dummyPerson = Person.builder()
            .ssn(ssn)
            .name(name)
            .surname(surname)
            .bornDate(bornDate)
            .country(country)
            .genre(genre)
            .build();

        mockFindOnePersonDependencies(dummyDBObject, dummyBasicDBObject, dummyPerson);

        context().start();

        Optional optionalPerson = template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY,
            FindOnePersonDTO.builder()
                .ssn(ssn)
                .country(country)
                .build(), Optional.class);

        assertEquals("Both persons are equals", dummyPerson, optionalPerson.get());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_OptionalWithPerson_IfBodyIsFindOnePersonDTOAndNotNullAndBeanValidAndPersonDoesNotExists()
        throws Exception {
        mock_Mongodb_Person_FindOneBy_SsnAndCountry_To_Fail();

        Long ssn = 10101010L;
        String country = Country.AR.name();

        DBObject dummyDBObject = BasicDBObjectBuilder.start()
            .add("ssn", ssn)
            .add("country", country)
            .get();

        mockFindOnePersonDependencies(dummyDBObject, null, null);

        context().start();

        Optional optionalPerson = template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY,
            FindOnePersonDTO.builder()
                .ssn(ssn)
                .country(country)
                .build(), Optional.class);

        assertFalse("Person not founded", optionalPerson.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_EmptyOptional_IfBodyIsFindOnePersonDTOAndNotNullAndBeanInvalidReasonSsnNull()
        throws Exception {
        Long ssn = null;
        String country = Country.AR.name();

        DBObject dummyDBObject = BasicDBObjectBuilder.start()
            .add("ssn", ssn)
            .add("country", country)
            .get();

        mockFindOnePersonDependencies(dummyDBObject, null, null);

        context().start();

        Optional optionalPerson = template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY,
            FindOnePersonDTO.builder()
                .ssn(ssn)
                .country(country)
                .build(), Optional.class);

        assertFalse("Person not founded", optionalPerson.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_EmptyOptional_IfBodyIsFindOnePersonDTOAndNotNullAndBeanInvalidReasonCountryNull()
        throws Exception {
        Long ssn = 11111111L;
        String country = null;

        DBObject dummyDBObject = BasicDBObjectBuilder.start()
            .add("ssn", ssn)
            .add("country", country)
            .get();

        mockFindOnePersonDependencies(dummyDBObject, null, null);

        context().start();

        Optional optionalPerson = template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY,
            FindOnePersonDTO.builder()
                .ssn(11111111L)
                .country(country)
                .build(), Optional.class);

        assertFalse("Person not founded", optionalPerson.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_EmptyOptional_IfBodyIsFindOnePersonDTOAndNotNullAndBeanInvalidReasonSsnAndCountryNull()
        throws Exception {
        Long ssn = null;
        String country = null;

        DBObject dummyDBObject = BasicDBObjectBuilder.start()
            .add("ssn", ssn)
            .add("country", country)
            .get();

        mockFindOnePersonDependencies(dummyDBObject, null, null);

        context().start();

        Optional optionalPerson = template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY,
            FindOnePersonDTO.builder()
                .ssn(ssn)
                .country(country)
                .build(), Optional.class);

        assertFalse("Person not founded", optionalPerson.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_EmptyOptional_IfBodyIsNull() throws Exception {
        mockFindOnePersonDependencies(null, null, null);

        context().start();

        Optional optionalPerson =
            template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, null, Optional.class);

        assertFalse("Person not founded", optionalPerson.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_EmptyOptional_IfBodyIsNotFindOnePersonDTOReasonByte() throws Exception {
        mockFindOnePersonDependencies(null, null, null);

        context().start();

        Optional optionalPersonByte =
            template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, (byte) 10110000, Optional.class);

        assertFalse("Unexpected class (Byte), person not founded", optionalPersonByte.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_EmptyOptional_IfBodyIsNotFindOnePersonDTOReasonShort() throws Exception {
        mockFindOnePersonDependencies(null, null, null);

        context().start();

        Optional optionalPersonShort =
            template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, (short) 2, Optional.class);

        assertFalse("Unexpected class (Short), person not founded", optionalPersonShort.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_EmptyOptional_IfBodyIsNotFindOnePersonDTOReasonInteger() throws Exception {
        mockFindOnePersonDependencies(null, null, null);

        context().start();

        Optional optionalPersonInteger =
            template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, 10101010, Optional.class);

        assertFalse("Unexpected class (Integer), person not founded", optionalPersonInteger.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_EmptyOptional_IfBodyIsNotFindOnePersonDTOReasonLong() throws Exception {
        mockFindOnePersonDependencies(null, null, null);

        context().start();

        Optional optionalPersonLong =
            template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, 10101010L, Optional.class);

        assertFalse("Unexpected class (Long), person not founded", optionalPersonLong.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_EmptyOptional_IfBodyIsNotFindOnePersonDTOReasonFloat() throws Exception {
        mockFindOnePersonDependencies(null, null, null);

        context().start();

        Optional optionalPersonFloat =
            template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, 10101010F, Optional.class);

        assertFalse("Unexpected class (Float), person not founded", optionalPersonFloat.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_EmptyOptional_IfBodyIsNotFindOnePersonDTOReasonDouble() throws Exception {
        mockFindOnePersonDependencies(null, null, null);

        context().start();

        Optional optionalPersonDouble =
            template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, 10101010D, Optional.class);

        assertFalse("Unexpected class (Double), person not founded", optionalPersonDouble.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_EmptyOptional_IfBodyIsNotFindOnePersonDTOReasonChar() throws Exception {
        mockFindOnePersonDependencies(null, null, null);

        context().start();

        Optional optionalPersonChar =
            template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, 's', Optional.class);

        assertFalse("Unexpected class (Char), person not founded", optionalPersonChar.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_EmptyOptional_IfBodyIsNotFindOnePersonDTOReasonString() throws Exception {
        mockFindOnePersonDependencies(null, null, null);

        context().start();

        Optional optionalPersonString =
            template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, "10101010", Optional.class);

        assertFalse("Unexpected class (String), person not founded", optionalPersonString.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_EmptyOptional_IfBodyIsNotFindOnePersonDTOReasonTrue() throws Exception {
        mockFindOnePersonDependencies(null, null, null);

        context().start();

        Optional optionalPersonBooleanTrue =
            template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, true, Optional.class);

        assertFalse("Unexpected class (Boolean True), person not founded", optionalPersonBooleanTrue.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_EmptyOptional_IfBodyIsNotFindOnePersonDTOReasonFalse() throws Exception {
        mockFindOnePersonDependencies(null, null, null);

        context().start();

        Optional optionalPersonBooleanFalse =
            template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, false, Optional.class);

        assertFalse("Unexpected class (Boolean False), person not founded", optionalPersonBooleanFalse.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_EmptyOptional_IfBodyIsNotFindOnePersonDTOReasonPerson() throws Exception {
        mockFindOnePersonDependencies(null, null, null);

        context().start();

        Optional optionalPersonEntity = template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY,
            Person.builder()
                .name("Luis")
                .surname("Gonzalez")
                .build(), Optional.class);

        assertFalse("Unexpected class (Entity), person not founded", optionalPersonEntity.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_EmptyOptional_IfBodyIsNotFindOnePersonDTOReasonPersonSameFields()
        throws Exception {
        mockFindOnePersonDependencies(null, null, null);

        context().start();

        Optional optionalPersonEntitySameFields = template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY,
            Person.builder()
                .ssn(11111111L)
                .country(Country.UY.name())
                .build(), Optional.class);

        assertFalse("Unexpected class (Entity) with same fields, person not founded",
            optionalPersonEntitySameFields.isPresent());

        context().stop();
    }

    @Test
    public void findOneBySsnAndCountry_EmptyOptional_IfBodyIsNotFindOnePersonDTOReasonList() throws Exception {
        mockFindOnePersonDependencies(null, null, null);

        context().start();

        List<FindOnePersonDTO> findOnePersonDTOList = new ArrayList<>();
        findOnePersonDTOList.add(FindOnePersonDTO.builder()
            .ssn(10101010L)
            .country(Country.BR.name())
            .build());
        findOnePersonDTOList.add(FindOnePersonDTO.builder()
            .ssn(10101011L)
            .country(Country.UY.name())
            .build());
        findOnePersonDTOList.add(FindOnePersonDTO.builder()
            .ssn(11111111L)
            .country(Country.UY.name())
            .build());

        Optional optionalPersonList =
            template().requestBody(Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY, findOnePersonDTOList, Optional.class);

        assertFalse("Unexpected class (List), person not founded", optionalPersonList.isPresent());

        context().stop();
    }

    /**
     * Redirects the findoneby database call to a
     * success endpoint.
     *
     * @throws Exception the exception
     */
    private void mock_Mongodb_Person_FindOneBy_SsnAndCountry_To_Success() throws Exception {
        AdviceWithRouteBuilder adviceWithRouteBuilder = new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                // intercept mongodb endpoint to mock:findOneByQuerySuccess and do something else
                interceptSendToEndpoint(Endpoint.MONGODB_PERSON_FINDONEBY).skipSendToOriginalEndpoint()
                    .setBody(basicDBObjectAsPersonSupplier);
            }
        };
        context().getRouteDefinition(RouteID.MONGODB_PERSON_FINDONEBY_SSNANDCOUNTRY.id())
            .adviceWith(context(), adviceWithRouteBuilder);
    }

    /**
     * Redirects the findoneby database call to a
     * fail endpoint.
     *
     * @throws Exception the exception
     */
    private void mock_Mongodb_Person_FindOneBy_SsnAndCountry_To_Fail() throws Exception {
        AdviceWithRouteBuilder adviceWithRouteBuilder = new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                // intercept mongodb endpoint to mock:findOneByQueryFail and do something else
                interceptSendToEndpoint(Endpoint.MONGODB_PERSON_FINDONEBY).skipSendToOriginalEndpoint()
                    .setBody(Optional::empty);
            }
        };
        context().getRouteDefinition(RouteID.MONGODB_PERSON_FINDONEBY_SSNANDCOUNTRY.id())
            .adviceWith(context(), adviceWithRouteBuilder);
    }

    /**
     * Mocks all {@link DatabaseResource} dependencies.
     *
     * @param dummyDBObject      the mongodb request object
     * @param dummyBasicDBObject the mongodb response object
     * @param dummyPerson        the route response object
     */
    private void mockFindOnePersonDependencies(DBObject dummyDBObject, BasicDBObject dummyBasicDBObject,
        Person dummyPerson) {
        doNothing().when(removeMongoIDProcessor)
            .process(any(Exchange.class));
        doReturn(dummyDBObject).when(findOnePersonBuildRequestFunction)
            .apply(any(Exchange.class));
        doReturn(dummyBasicDBObject).when(basicDBObjectAsPersonSupplier)
            .get();
        doReturn(Optional.ofNullable(dummyBasicDBObject)).when(asOptionalFunction)
            .apply(any(Exchange.class));
        doReturn(Optional.ofNullable(dummyPerson)).when(findOnePersonBuildResponseFunction)
            .apply(any(Exchange.class));
    }

    @Test
    public void insertDBPerson_NoException_IfBodyIsPersonAndNotNullAndBeanValidAndDBRequestSuccess() throws Exception {
        mock_Mongodb_Person_Insert_MongoNothing();

        context.start();

        template().sendBody(Endpoint.DIRECT_INSERT_DB_PERSON, Person.builder()
            .ssn(11111111L)
            .name("Luis")
            .surname("Malo")
            .bornDate("17-04-1986")
            .country(Country.UY.name())
            .genre("Male")
            .build());

        assertTrue("Person successfully inserted", true);

        context.stop();
    }

    @Test
    public void insertDBPerson_PersonInsertionException_IfBodyIsPersonAndNotNullAndBeanValidAndDBRequestFailReasonExistentPerson()
        throws Exception {
        mock_Mongodb_Person_Insert_MongoException();

        context.start();

        Exchange exchangeIn =
            getMandatoryEndpoint(Endpoint.DIRECT_INSERT_DB_PERSON).createExchange(ExchangePattern.InOut);
        exchangeIn.getIn()
            .setBody(Person.builder()
                .ssn(11111111L)
                .name("Luis")
                .surname("Malo")
                .bornDate("17-04-1986")
                .country(Country.UY.name())
                .genre("Male")
                .build());

        Exchange exchangeOut = template().send(Endpoint.DIRECT_INSERT_DB_PERSON, exchangeIn);

        assertIsInstanceOf(PersonInsertionException.class, exchangeOut.getException());

        context.stop();
    }

    @Test
    public void insertDBPerson_NullBodyException_IfBodyNullReasonBodyNull() throws Exception {
        mock_Mongodb_Person_Insert_MongoException();

        context.start();

        Exchange exchangeIn =
            getMandatoryEndpoint(Endpoint.DIRECT_INSERT_DB_PERSON).createExchange(ExchangePattern.InOut);
        exchangeIn.getIn()
            .setBody(null);

        Exchange exchangeOut = template().send(Endpoint.DIRECT_INSERT_DB_PERSON, exchangeIn);

        assertIsInstanceOf(NullBodyException.class, exchangeOut.getException());

        context.stop();
    }

    @Test
    public void insertDBPerson_NullBodyException_IfBodyNullReasonBodyEmpty() throws Exception {
        mock_Mongodb_Person_Insert_MongoException();

        context.start();

        Exchange exchangeIn =
            getMandatoryEndpoint(Endpoint.DIRECT_INSERT_DB_PERSON).createExchange(ExchangePattern.InOut);

        Exchange exchangeOut = template().send(Endpoint.DIRECT_INSERT_DB_PERSON, exchangeIn);
        assertIsInstanceOf(NullBodyException.class, exchangeOut.getException());

        context.stop();
    }

    @Test
    public void insertDBPerson_PersonInsertionException_IfBodyIsPersonAndNotNullAndBeanInvalidReasonSsnNull()
        throws Exception {
        mock_Mongodb_Person_Insert_MongoNothing();

        context.start();

        Exchange exchangeIn =
            getMandatoryEndpoint(Endpoint.DIRECT_INSERT_DB_PERSON).createExchange(ExchangePattern.InOut);
        exchangeIn.getIn()
            .setBody(Person.builder()
                .ssn(null)
                .name("Luis")
                .surname("Malo")
                .bornDate("17-04-1986")
                .country(Country.UY.name())
                .genre("Male")
                .build());

        Exchange exchangeOut = template().send(Endpoint.DIRECT_INSERT_DB_PERSON, exchangeIn);
        assertIsInstanceOf(InvalidPersonFieldException.class, exchangeOut.getException());

        context.stop();
    }

    @Test
    public void insertDBPerson_PersonInsertionException_IfBodyIsPersonAndNotNullAndBeanInvalidReasonSsnLess1000000()
        throws Exception {
        mock_Mongodb_Person_Insert_MongoNothing();

        context.start();

        Exchange exchangeIn =
            getMandatoryEndpoint(Endpoint.DIRECT_INSERT_DB_PERSON).createExchange(ExchangePattern.InOut);
        exchangeIn.getIn()
            .setBody(Person.builder()
                .ssn(500L)
                .name("Luis")
                .surname("Malo")
                .bornDate("17-04-1986")
                .country(Country.UY.name())
                .genre("Male")
                .build());

        Exchange exchangeOut = template().send(Endpoint.DIRECT_INSERT_DB_PERSON, exchangeIn);
        assertIsInstanceOf(InvalidPersonFieldException.class, exchangeOut.getException());

        context.stop();
    }

    @Test
    public void insertDBPerson_PersonInsertionException_IfBodyIsPersonAndNotNullAndBeanInvalidReasonSsnMore999999999()
        throws Exception {
        mock_Mongodb_Person_Insert_MongoNothing();

        context.start();

        Exchange exchangeIn =
            getMandatoryEndpoint(Endpoint.DIRECT_INSERT_DB_PERSON).createExchange(ExchangePattern.InOut);
        exchangeIn.getIn()
            .setBody(Person.builder()
                .ssn(99999999999999L)
                .name("Luis")
                .surname("Malo")
                .bornDate("17-04-1986")
                .country(Country.UY.name())
                .genre("Male")
                .build());

        Exchange exchangeOut = template().send(Endpoint.DIRECT_INSERT_DB_PERSON, exchangeIn);
        assertIsInstanceOf(InvalidPersonFieldException.class, exchangeOut.getException());

        context.stop();
    }

    @Test
    public void insertDBPerson_PersonInsertionException_IfBodyIsPersonAndNotNullAndBeanInvalidReasonNameNull()
        throws Exception {
        mock_Mongodb_Person_Insert_MongoNothing();

        context.start();

        Exchange exchangeIn =
            getMandatoryEndpoint(Endpoint.DIRECT_INSERT_DB_PERSON).createExchange(ExchangePattern.InOut);
        exchangeIn.getIn()
            .setBody(Person.builder()
                .ssn(11111111L)
                .name(null)
                .surname("Malo")
                .bornDate("17-04-1986")
                .country(Country.UY.name())
                .genre("Male")
                .build());

        Exchange exchangeOut = template().send(Endpoint.DIRECT_INSERT_DB_PERSON, exchangeIn);
        assertIsInstanceOf(InvalidPersonFieldException.class, exchangeOut.getException());

        context.stop();
    }

    @Test
    public void insertDBPerson_PersonInsertionException_IfBodyIsPersonAndNotNullAndBeanInvalidReasonNameBlank()
        throws Exception {
        mock_Mongodb_Person_Insert_MongoNothing();

        context.start();

        Exchange exchangeIn =
            getMandatoryEndpoint(Endpoint.DIRECT_INSERT_DB_PERSON).createExchange(ExchangePattern.InOut);
        exchangeIn.getIn()
            .setBody(Person.builder()
                .ssn(11111111L)
                .name("")
                .surname("Malo")
                .bornDate("17-04-1986")
                .country(Country.UY.name())
                .genre("Male")
                .build());

        Exchange exchangeOut = template().send(Endpoint.DIRECT_INSERT_DB_PERSON, exchangeIn);
        assertIsInstanceOf(InvalidPersonFieldException.class, exchangeOut.getException());

        context.stop();
    }

    @Test
    public void insertDBPerson_PersonInsertionException_IfBodyIsPersonAndNotNullAndBeanInvalidReasonSurnameNull()
        throws Exception {
        mock_Mongodb_Person_Insert_MongoNothing();

        context.start();

        Exchange exchangeIn =
            getMandatoryEndpoint(Endpoint.DIRECT_INSERT_DB_PERSON).createExchange(ExchangePattern.InOut);
        exchangeIn.getIn()
            .setBody(Person.builder()
                .ssn(11111111L)
                .name("Luis")
                .surname(null)
                .bornDate("17-04-1986")
                .country(Country.UY.name())
                .genre("Male")
                .build());

        Exchange exchangeOut = template().send(Endpoint.DIRECT_INSERT_DB_PERSON, exchangeIn);
        assertIsInstanceOf(InvalidPersonFieldException.class, exchangeOut.getException());

        context.stop();
    }

    @Test
    public void insertDBPerson_PersonInsertionException_IfBodyIsPersonAndNotNullAndBeanInvalidReasonSurnameBlank()
        throws Exception {
        mock_Mongodb_Person_Insert_MongoNothing();

        context.start();

        Exchange exchangeIn =
            getMandatoryEndpoint(Endpoint.DIRECT_INSERT_DB_PERSON).createExchange(ExchangePattern.InOut);
        exchangeIn.getIn()
            .setBody(Person.builder()
                .ssn(11111111L)
                .name("Luis")
                .surname("")
                .bornDate("17-04-1986")
                .country(Country.UY.name())
                .genre("Male")
                .build());

        Exchange exchangeOut = template().send(Endpoint.DIRECT_INSERT_DB_PERSON, exchangeIn);
        assertIsInstanceOf(InvalidPersonFieldException.class, exchangeOut.getException());

        context.stop();
    }

    @Test
    public void insertDBPerson_PersonInsertionException_IfBodyIsPersonAndNotNullAndBeanInvalidReasonBornDateNull()
        throws Exception {
        mock_Mongodb_Person_Insert_MongoNothing();

        context.start();

        Exchange exchangeIn =
            getMandatoryEndpoint(Endpoint.DIRECT_INSERT_DB_PERSON).createExchange(ExchangePattern.InOut);
        exchangeIn.getIn()
            .setBody(Person.builder()
                .ssn(11111111L)
                .name("Luis")
                .surname("Malo")
                .bornDate(null)
                .country(Country.UY.name())
                .genre("Male")
                .build());

        Exchange exchangeOut = template().send(Endpoint.DIRECT_INSERT_DB_PERSON, exchangeIn);
        assertIsInstanceOf(InvalidPersonFieldException.class, exchangeOut.getException());

        context.stop();
    }

    @Test
    public void insertDBPerson_PersonInsertionException_IfBodyIsPersonAndNotNullAndBeanInvalidReasonBornDateBlank()
        throws Exception {
        mock_Mongodb_Person_Insert_MongoNothing();

        context.start();

        Exchange exchangeIn =
            getMandatoryEndpoint(Endpoint.DIRECT_INSERT_DB_PERSON).createExchange(ExchangePattern.InOut);
        exchangeIn.getIn()
            .setBody(Person.builder()
                .ssn(11111111L)
                .name("Luis")
                .surname("Malo")
                .bornDate("")
                .country(Country.UY.name())
                .genre("Male")
                .build());

        Exchange exchangeOut = template().send(Endpoint.DIRECT_INSERT_DB_PERSON, exchangeIn);
        assertIsInstanceOf(InvalidPersonFieldException.class, exchangeOut.getException());

        context.stop();
    }

    @Test
    public void insertDBPerson_PersonInsertionException_IfBodyIsPersonAndNotNullAndBeanInvalidReasonCountryNull()
        throws Exception {
        mock_Mongodb_Person_Insert_MongoNothing();

        context.start();

        Exchange exchangeIn =
            getMandatoryEndpoint(Endpoint.DIRECT_INSERT_DB_PERSON).createExchange(ExchangePattern.InOut);
        exchangeIn.getIn()
            .setBody(Person.builder()
                .ssn(11111111L)
                .name("Luis")
                .surname("Malo")
                .bornDate("17-04-1986")
                .country(null)
                .genre("Male")
                .build());

        Exchange exchangeOut = template().send(Endpoint.DIRECT_INSERT_DB_PERSON, exchangeIn);
        assertIsInstanceOf(InvalidPersonFieldException.class, exchangeOut.getException());

        context.stop();
    }

    @Test
    public void insertDBPerson_PersonInsertionException_IfBodyIsPersonAndNotNullAndBeanInvalidReasonCountryBlank()
        throws Exception {
        mock_Mongodb_Person_Insert_MongoNothing();

        context.start();

        Exchange exchangeIn =
            getMandatoryEndpoint(Endpoint.DIRECT_INSERT_DB_PERSON).createExchange(ExchangePattern.InOut);
        exchangeIn.getIn()
            .setBody(Person.builder()
                .ssn(11111111L)
                .name("Luis")
                .surname("Malo")
                .bornDate("17-04-1986")
                .country("")
                .genre("Male")
                .build());

        Exchange exchangeOut = template().send(Endpoint.DIRECT_INSERT_DB_PERSON, exchangeIn);
        assertIsInstanceOf(InvalidPersonFieldException.class, exchangeOut.getException());

        context.stop();
    }

    @Test
    public void insertDBPerson_PersonInsertionException_IfBodyIsPersonAndNotNullAndBeanInvalidReasonCountryLess2()
        throws Exception {
        mock_Mongodb_Person_Insert_MongoNothing();

        context.start();

        Exchange exchangeIn =
            getMandatoryEndpoint(Endpoint.DIRECT_INSERT_DB_PERSON).createExchange(ExchangePattern.InOut);
        exchangeIn.getIn()
            .setBody(Person.builder()
                .ssn(11111111L)
                .name("Luis")
                .surname("Malo")
                .bornDate("17-04-1986")
                .country("A")
                .genre("Male")
                .build());

        Exchange exchangeOut = template().send(Endpoint.DIRECT_INSERT_DB_PERSON, exchangeIn);
        assertIsInstanceOf(InvalidPersonFieldException.class, exchangeOut.getException());

        context.stop();
    }

    @Test
    public void insertDBPerson_PersonInsertionException_IfBodyIsPersonAndNotNullAndBeanInvalidReasonCountryMore2()
        throws Exception {
        mock_Mongodb_Person_Insert_MongoNothing();

        context.start();

        Exchange exchangeIn =
            getMandatoryEndpoint(Endpoint.DIRECT_INSERT_DB_PERSON).createExchange(ExchangePattern.InOut);
        exchangeIn.getIn()
            .setBody(Person.builder()
                .ssn(11111111L)
                .name("Luis")
                .surname("Malo")
                .bornDate("17-04-1986")
                .country("ARR")
                .genre("Male")
                .build());

        Exchange exchangeOut = template().send(Endpoint.DIRECT_INSERT_DB_PERSON, exchangeIn);
        assertIsInstanceOf(InvalidPersonFieldException.class, exchangeOut.getException());

        context.stop();
    }

    @Test
    public void insertDBPerson_PersonInsertionException_IfBodyIsPersonAndNotNullAndBeanInvalidReasonGenreNull()
        throws Exception {
        mock_Mongodb_Person_Insert_MongoNothing();

        context.start();

        Exchange exchangeIn =
            getMandatoryEndpoint(Endpoint.DIRECT_INSERT_DB_PERSON).createExchange(ExchangePattern.InOut);
        exchangeIn.getIn()
            .setBody(Person.builder()
                .ssn(11111111L)
                .name("Luis")
                .surname("Malo")
                .bornDate("17-04-1986")
                .country(Country.AR.name())
                .genre(null)
                .build());

        Exchange exchangeOut = template().send(Endpoint.DIRECT_INSERT_DB_PERSON, exchangeIn);
        assertIsInstanceOf(InvalidPersonFieldException.class, exchangeOut.getException());

        context.stop();
    }

    @Test
    public void insertDBPerson_PersonInsertionException_IfBodyIsPersonAndNotNullAndBeanInvalidReasonGenreBlank()
        throws Exception {
        mock_Mongodb_Person_Insert_MongoNothing();

        context.start();

        Exchange exchangeIn =
            getMandatoryEndpoint(Endpoint.DIRECT_INSERT_DB_PERSON).createExchange(ExchangePattern.InOut);
        exchangeIn.getIn()
            .setBody(Person.builder()
                .ssn(11111111L)
                .name("Luis")
                .surname("Malo")
                .bornDate("17-04-1986")
                .country(Country.AR.name())
                .genre("")
                .build());

        Exchange exchangeOut = template().send(Endpoint.DIRECT_INSERT_DB_PERSON, exchangeIn);
        assertIsInstanceOf(InvalidPersonFieldException.class, exchangeOut.getException());

        context.stop();
    }

    @Test
    public void insertDBPerson_PersonInsertionException_IfBodyIsNotPersonReasonFindOnePersonDTO() throws Exception {
        mock_Mongodb_Person_Insert_MongoException();

        context.start();

        Exchange exchangeIn =
            getMandatoryEndpoint(Endpoint.DIRECT_INSERT_DB_PERSON).createExchange(ExchangePattern.InOut);
        exchangeIn.getIn()
            .setBody(FindOnePersonDTO.builder()
                .ssn(111L)
                .country(Country.AR.name())
                .build());

        Exchange exchangeOut = template().send(Endpoint.DIRECT_INSERT_DB_PERSON, exchangeIn);
        assertIsInstanceOf(NullBodyException.class, exchangeOut.getException());

        context.stop();
    }

    /**
     * Do not do nothing when insert the
     * person into the database.
     *
     * @throws Exception the exception
     */
    private void mock_Mongodb_Person_Insert_MongoNothing() throws Exception {
        AdviceWithRouteBuilder adviceWithRouteBuilder = new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                // intercept mongodb endpoint to do not do nothing
                interceptSendToEndpoint(Endpoint.MONGODB_PERSON_INSERT).skipSendToOriginalEndpoint()
                    .log("Don't do nothing");
            }
        };
        context().getRouteDefinition(RouteID.MONGODB_PERSON_INSERT.id())
            .adviceWith(context(), adviceWithRouteBuilder);
    }

    /**
     * Throw MongoException when insert the
     * duplicated person into the database.
     *
     * @throws Exception the exception
     */
    private void mock_Mongodb_Person_Insert_MongoException() throws Exception {
        AdviceWithRouteBuilder adviceWithRouteBuilder = new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                // intercept mongodb endpoint to throw MongoException
                interceptSendToEndpoint(Endpoint.MONGODB_PERSON_INSERT).skipSendToOriginalEndpoint()
                    .throwException(MongoException.class, "Duplicated key violation");
            }
        };
        context().getRouteDefinition(RouteID.MONGODB_PERSON_INSERT.id())
            .adviceWith(context(), adviceWithRouteBuilder);
    }
}
