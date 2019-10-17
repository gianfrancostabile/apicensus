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

    public static String BEAN_VALIDATOR_DEFAULT_GROUP, DIRECT_MISMATCHED_INPUT_FILE, DIRECT_BAD_REQUEST,
        DIRECT_DEFAULT_ENDPOINT, DIRECT_FINDONEBY_SSN_AND_COUNTRY, DIRECT_FINDONEBYQUERY_FAIL,
        DIRECT_FINDONEBYQUERY_SUCCESS, DIRECT_FINDONEBY_SSN_AND_COUNTRY_CREATEREQUEST,
        DIRECT_FINDONEBY_SSN_AND_COUNTRY_CREATERESPONSE, DIRECT_INSERT_DB_PERSON, DIRECT_INTERNAL_SERVER_ERROR,
        DIRECT_SET_SAME_BODY, FILE_PERSON_FTP, MONGODB_PERSON_FINDONEBY_SSNANDCOUNTRY, MONGODB_PERSON_INSERT,
        REDIRECT_SWAGGER;

    private static String FILE_PEOPLE_FTP_FOLDER, FILE_PEOPLE_FTP_FILE, PERSON_MONGO_DB_ENDPOINT;

    private Endpoint(@Value("${file.people.ftp.folder}") String filePeopleFtpFolder,
        @Value("${file.people.ftp.file}") String filePeopleFtpFile,
        @Value("${camel.endpoint.mongodb.people}") String personMongoDbEndpoint) {
        FILE_PEOPLE_FTP_FOLDER = filePeopleFtpFolder;
        FILE_PEOPLE_FTP_FILE = filePeopleFtpFile;
        PERSON_MONGO_DB_ENDPOINT = personMongoDbEndpoint;

        updateStatics();
    }

    private void updateStatics() {
        BEAN_VALIDATOR_DEFAULT_GROUP = "bean-validator://x";
        DIRECT_MISMATCHED_INPUT_FILE = "direct:mismatchedInputFileHandler";
        DIRECT_BAD_REQUEST = "direct:badRequest";
        DIRECT_DEFAULT_ENDPOINT = "direct:defaultEndpoint";
        DIRECT_FINDONEBY_SSN_AND_COUNTRY = "direct:findOneBySsnAndCountry";
        DIRECT_FINDONEBY_SSN_AND_COUNTRY_CREATEREQUEST = "direct:findOneBySsnAndCountryRequest";
        DIRECT_FINDONEBY_SSN_AND_COUNTRY_CREATERESPONSE = "direct:findOneBySsnAndCountryResponse";
        DIRECT_FINDONEBYQUERY_FAIL = "direct:findOneByQueryFail";
        DIRECT_FINDONEBYQUERY_SUCCESS = "direct:findOneByQuerySuccess";
        DIRECT_INSERT_DB_PERSON = "direct:insertDBPerson";
        DIRECT_INTERNAL_SERVER_ERROR = "direct:internalServerError";
        DIRECT_SET_SAME_BODY = "direct:setSameBody";
        FILE_PERSON_FTP = "file:" + FILE_PEOPLE_FTP_FOLDER + "?delete=true&antInclude=" + FILE_PEOPLE_FTP_FILE;
        MONGODB_PERSON_FINDONEBY_SSNANDCOUNTRY = PERSON_MONGO_DB_ENDPOINT + "findOneByQuery";
        MONGODB_PERSON_INSERT = PERSON_MONGO_DB_ENDPOINT + "insert";
        REDIRECT_SWAGGER = "/webjars/swagger-ui/index.html?url=/census/swagger&validatorUrl=";
    }
}
