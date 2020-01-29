package com.accenture.java.apicensus.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RouteIDTest {

    @Test
    public void id_ActiveMQInsertPeople_WithoutArguments() {
        assertEquals("RouteID.ACTIVEMQ_INSERT_PEOPLE id should be activemq-insert-people", "activemq-insert-people",
            RouteID.ACTIVEMQ_INSERT_PEOPLE.id());
    }

    @Test
    public void id_Authenticate_WithoutArguments() {
        assertEquals("RouteID.AUTHENTICATE id should be authenticate", "authenticate",
            RouteID.AUTHENTICATE.id());
    }

    @Test
    public void id_AuthenticationManager_WithoutArguments() {
        assertEquals("RouteID.AUTHENTICATION_MANAGER id should be authentication-manager", "authentication-manager",
            RouteID.AUTHENTICATION_MANAGER.id());
    }

    @Test
    public void id_AuthenticatePost_WithoutArguments() {
        assertEquals("RouteID.AUTHENTICATE_POST id should be authenticate-post", "authenticate-post",
            RouteID.AUTHENTICATE_POST.id());
    }

    @Test
    public void id_BadRequest_WithoutArguments() {
        assertEquals("RouteID.BAD_REQUEST id should be bad-request", "bad-request", RouteID.BAD_REQUEST.id());
    }

    @Test
    public void id_CountriesGet_WithoutArguments() {
        assertEquals("RouteID.COUNTRIES_GET id should be countries-get", "countries-get", RouteID.COUNTRIES_GET.id());
    }

    @Test
    public void id_DefaultRoute_WithoutArguments() {
        assertEquals("RouteID.DEFAULT_ROUTE id should be default-route", "default-route", RouteID.DEFAULT_ROUTE.id());
    }

    @Test
    public void id_DoInsertPeople_WithoutArguments() {
        assertEquals("RouteID.DO_INSERT_PEOPLE id should be do-insert-people", "do-insert-people",
            RouteID.DO_INSERT_PEOPLE.id());
    }

    @Test
    public void id_FilePersonFtp_WithoutArguments() {
        assertEquals("RouteID.FILE_PERSON_FTP id should be file-person-ftp", "file-person-ftp",
            RouteID.FILE_PERSON_FTP.id());
    }

    @Test
    public void id_InsertUser_WithoutArguments() {
        assertEquals("RouteID.INSERT_USER id should be insert-user", "insert-user", RouteID.INSERT_USER.id());
    }

    @Test
    public void id_InternalServerError_WithoutArguments() {
        assertEquals("RouteID.INTERNAL_SERVER_ERROR id should be internal-server-error", "internal-server-error",
            RouteID.INTERNAL_SERVER_ERROR.id());
    }

    @Test
    public void id_MismatchedInputFile_WithoutArguments() {
        assertEquals("RouteID.MISMATCHED_INPUT_FILE id should be mismatched-input-file", "mismatched-input-file",
            RouteID.MISMATCHED_INPUT_FILE.id());
    }

    @Test
    public void id_MongodbPersonFindonebySsnandcountry_WithoutArguments() {
        assertEquals(
            "RouteID.MONGODB_PERSON_FINDONEBY_SSNANDCOUNTRY id should be mongodb-person-findoneby-ssnandcountry",
            "mongodb-person-findoneby-ssnandcountry", RouteID.MONGODB_PERSON_FINDONEBY_SSNANDCOUNTRY.id());
    }

    @Test
    public void id_MongodbPersonInsert_WithoutArguments() {
        assertEquals("RouteID.MONGODB_PERSON_INSERT id should be mongodb-person-insert", "mongodb-person-insert",
            RouteID.MONGODB_PERSON_INSERT.id());
    }

    @Test
    public void id_PersonPost_WithoutArguments() {
        assertEquals("RouteID.PERSON_POST id should be person-post", "person-post", RouteID.PERSON_POST.id());
    }

    @Test
    public void id_RedirectSwagger_WithoutArguments() {
        assertEquals("RouteID.REDIRECT_SWAGGER id should be redirect-swagger", "redirect-swagger",
            RouteID.REDIRECT_SWAGGER.id());
    }

    @Test
    public void id_SetEmptyResponselist_WithoutArguments() {
        assertEquals("RouteID.SET_EMPTY_RESPONSELIST id should be set-empty-response-list", "set-empty-response-list",
            RouteID.SET_EMPTY_RESPONSELIST.id());
    }

    @Test
    public void id_Swagger_WithoutArguments() {
        assertEquals("RouteID.SWAGGER id should be swagger", "swagger", RouteID.SWAGGER.id());
    }

    @Test
    public void id_UserPost_WithoutArguments() {
        assertEquals("RouteID.USER_POST id should be user-post", "user-post", RouteID.USER_POST.id());
    }
}
