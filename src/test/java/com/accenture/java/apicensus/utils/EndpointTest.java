package com.accenture.java.apicensus.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Endpoint.class)
@TestPropertySource(locations = "classpath:endpoint-test.properties")
public class EndpointTest {

    @Test
    public void endpoint_ActiveMQInsertPeople_WithoutArguments() {
        assertEquals("Endpoint.ACTIVEMQ_INSERT_PEOPLE", "activemq:queue:insertPeople", Endpoint.ACTIVEMQ_INSERT_PEOPLE);
    }

    @Test
    public void endpoint_Authenticate_WithoutArguments() {
        assertEquals("Endpoint.AUTHENTICATE", "/census/authenticate", Endpoint.AUTHENTICATE);
    }

    @Test
    public void endpoint_DirectAuthenticate_WithoutArguments() {
        assertEquals("Endpoint.DIRECT_AUTHENTICATE", "direct:authenticate", Endpoint.DIRECT_AUTHENTICATE);
    }

    @Test
    public void endpoint_DirectAuthenticateWithManager_WithoutArguments() {
        assertEquals("Endpoint.DIRECT_AUTHENTICATE_WITH_MANAGER", "direct:authenticateWithManager",
            Endpoint.DIRECT_AUTHENTICATE_WITH_MANAGER);
    }

    @Test
    public void endpoint_BeanValidatorDefaultGroup_WithoutArguments() {
        assertEquals("Endpoint.BEAN_VALIDATOR_DEFAULT_GROUP", "bean-validator://x",
            Endpoint.BEAN_VALIDATOR_DEFAULT_GROUP);
    }

    @Test
    public void endpoint_DirectBadRequest_WithoutArguments() {
        assertEquals("Endpoint.DIRECT_BAD_REQUEST", "direct:badRequest", Endpoint.DIRECT_BAD_REQUEST);
    }

    @Test
    public void endpoint_DirectDefaultEndpoint_WithoutArguments() {
        assertEquals("Endpoint.DIRECT_DEFAULT_ENDPOINT", "direct:defaultEndpoint", Endpoint.DIRECT_DEFAULT_ENDPOINT);
    }

    @Test
    public void endpoint_DirectDoInsertPeople_WithoutArguments() {
        assertEquals("Endpoint.DIRECT_DO_INSERT_PEOPLE", "direct:doInsertPeople", Endpoint.DIRECT_DO_INSERT_PEOPLE);
    }

    @Test
    public void endpoint_DirectFindonebySsnAndCountry_WithoutArguments() {
        assertEquals("Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY", "direct:findOneBySsnAndCountry",
            Endpoint.DIRECT_FINDONEBY_SSN_AND_COUNTRY);
    }

    @Test
    public void endpoint_DirectInsertDbPerson_WithoutArguments() {
        assertEquals("Endpoint.DIRECT_INSERT_DB_PERSON", "direct:insertDBPerson", Endpoint.DIRECT_INSERT_DB_PERSON);
    }

    @Test
    public void endpoint_DirectInsertUser_WithoutArguments() {
        assertEquals("Endpoint.DIRECT_INSERT_USER", "direct:insertUser", Endpoint.DIRECT_INSERT_USER);
    }

    @Test
    public void endpoint_DirectInternalServerError_WithoutArguments() {
        assertEquals("Endpoint.DIRECT_INTERNAL_SERVER_ERROR", "direct:internalServerError",
            Endpoint.DIRECT_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void endpoint_DirectMismatchedInputFile_WithoutArguments() {
        assertEquals("Endpoint.DIRECT_MISMATCHED_INPUT_FILE", "direct:mismatchedInputFileHandler",
            Endpoint.DIRECT_MISMATCHED_INPUT_FILE);
    }

    @Test
    public void endpoint_DirectSetEmptyResponseList_WithoutArguments() {
        assertEquals("Endpoint.DIRECT_SET_EMPTY_RESPONSELIST", "direct:setEmptyResponseList",
            Endpoint.DIRECT_SET_EMPTY_RESPONSELIST);
    }

    @Test
    public void endpoint_FilePersonFtp_WithoutArguments() {
        assertEquals("Endpoint.FILE_PERSON_FTP", "file:to/the/folder?delete=true&antInclude=file.json",
            Endpoint.FILE_PERSON_FTP);
    }

    @Test
    public void endpoint_MongodbPersonFindoneby_WithoutArguments() {
        assertEquals("Endpoint.MONGODB_PERSON_FINDONEBY",
            "mongodb:mongoDBBean?database=myDatabaseName&collection=people&operation=findOneByQuery",
            Endpoint.MONGODB_PERSON_FINDONEBY);
    }

    @Test
    public void endpoint_MongodbPersonInsert_WithoutArguments() {
        assertEquals("Endpoint.MONGODB_PERSON_INSERT",
            "mongodb:mongoDBBean?database=myDatabaseName&collection=people&operation=insert",
            Endpoint.MONGODB_PERSON_INSERT);
    }

    @Test
    public void endpoint_MongodbUserInsert_WithoutArguments() {
        assertEquals("Endpoint.MONGODB_USER_INSERT",
            "mongodb:mongoDBBean?database=myDatabaseName&collection=users&operation=insert",
            Endpoint.MONGODB_USER_INSERT);
    }

    @Test
    public void endpoint_RedirectSwagger_WithoutArguments() {
        assertEquals("Endpoint.REDIRECT_SWAGGER", "/webjars/swagger-ui/index.html?url=/census/swagger&validatorUrl=",
            Endpoint.REDIRECT_SWAGGER);
    }

    @Test
    public void endpoint_SwaggerEndpoints_WithoutArguments() {
        String[] expected = new String[] {
            "/census/swagger-ui", "/webjars/swagger-ui/index.html", "/v2/api-docs", "/webjars/**",
            "/swagger-resources/**", "/configuration/**", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css",
            "/**/*.js", "/census/swagger"
        };
        assertArrayEquals("Endpoint.SWAGGER_ENDPOINTS", expected, Endpoint.SWAGGER_ENDPOINTS);
    }

    @Test
    public void endpoint_User_WithoutArguments() {
        assertEquals("Endpoint.USER", "/census/user", Endpoint.USER);
    }
}
