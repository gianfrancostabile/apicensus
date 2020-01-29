package com.accenture.java.apicensus.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Enum with a reference to all created endpoints.
 * <br><br>
 * Each value is sorted alphabetically.
 *
 * @author Gian F. S.
 */
@Component
public class Endpoint {
    public static String ACTIVEMQ_INSERT_PEOPLE;
    public static String AUTHENTICATE;
    public static String BEAN_VALIDATOR_DEFAULT_GROUP;
    public static String DIRECT_AUTHENTICATE;
    public static String DIRECT_AUTHENTICATE_WITH_MANAGER;
    public static String DIRECT_MISMATCHED_INPUT_FILE;
    public static String DIRECT_BAD_REQUEST;
    public static String DIRECT_DEFAULT_ENDPOINT;
    public static String DIRECT_DO_INSERT_PEOPLE;
    public static String DIRECT_FINDONEBY_SSN_AND_COUNTRY;
    public static String DIRECT_INSERT_DB_PERSON;
    public static String DIRECT_INSERT_USER;
    public static String DIRECT_INTERNAL_SERVER_ERROR;
    public static String DIRECT_SET_EMPTY_RESPONSELIST;
    public static String FILE_PERSON_FTP;
    public static String MONGODB_PERSON_FINDONEBY;
    public static String MONGODB_PERSON_INSERT;
    public static String MONGODB_USER_INSERT;
    public static String REDIRECT_SWAGGER;
    public static String USER;
    public static String[] SWAGGER_ENDPOINTS;

    private static String API_SWAGGER_UI;
    private static String WEBJAR_SWAGGER;
    private static String FILE_PEOPLE_FTP_FILE;
    private static String FILE_PEOPLE_FTP_FOLDER;
    private static String PERSON_MONGO_DB_ENDPOINT;
    private static String USERS_MONGO_DB_ENDPOINT;

    private Endpoint(@Value("${file.people.ftp.file}") String filePeopleFtpFile,
        @Value("${file.people.ftp.folder}") String filePeopleFtpFolder,
        @Value("${camel.endpoint.mongodb.people}") String personMongoDbEndpoint,
        @Value("${camel.endpoint.mongodb.users}") String usersMongoDbEndpoint) {
        FILE_PEOPLE_FTP_FILE = filePeopleFtpFile;
        FILE_PEOPLE_FTP_FOLDER = filePeopleFtpFolder;
        PERSON_MONGO_DB_ENDPOINT = personMongoDbEndpoint;
        USERS_MONGO_DB_ENDPOINT = usersMongoDbEndpoint;

        updateStatics();
    }

    private static void updateStatics() {
        ACTIVEMQ_INSERT_PEOPLE = "activemq:queue:insertPeople";
        API_SWAGGER_UI = "/census/swagger-ui";
        AUTHENTICATE = "/census/authenticate";
        BEAN_VALIDATOR_DEFAULT_GROUP = "bean-validator://x";
        DIRECT_AUTHENTICATE = "direct:authenticate";
        DIRECT_AUTHENTICATE_WITH_MANAGER = "direct:authenticateWithManager";
        DIRECT_MISMATCHED_INPUT_FILE = "direct:mismatchedInputFileHandler";
        DIRECT_BAD_REQUEST = "direct:badRequest";
        DIRECT_DEFAULT_ENDPOINT = "direct:defaultEndpoint";
        DIRECT_DO_INSERT_PEOPLE = "direct:doInsertPeople";
        DIRECT_FINDONEBY_SSN_AND_COUNTRY = "direct:findOneBySsnAndCountry";
        DIRECT_INSERT_DB_PERSON = "direct:insertDBPerson";
        DIRECT_INSERT_USER = "direct:insertUser";
        DIRECT_INTERNAL_SERVER_ERROR = "direct:internalServerError";
        DIRECT_SET_EMPTY_RESPONSELIST = "direct:setEmptyResponseList";
        FILE_PERSON_FTP = "file:" + FILE_PEOPLE_FTP_FOLDER + "?delete=true&antInclude=" + FILE_PEOPLE_FTP_FILE;
        MONGODB_PERSON_FINDONEBY = PERSON_MONGO_DB_ENDPOINT + "findOneByQuery";
        MONGODB_PERSON_INSERT = PERSON_MONGO_DB_ENDPOINT + "insert";
        MONGODB_USER_INSERT = USERS_MONGO_DB_ENDPOINT + "insert";
        WEBJAR_SWAGGER = "/webjars/swagger-ui/index.html";
        REDIRECT_SWAGGER = WEBJAR_SWAGGER + "?url=/census/swagger&validatorUrl=";
        SWAGGER_ENDPOINTS = new String[] {
            API_SWAGGER_UI, WEBJAR_SWAGGER, "/v2/api-docs", "/webjars/**", "/swagger-resources/**", "/configuration/**",
            "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js", "/census/swagger"
        };
        USER = "/census/user";
    }
}
