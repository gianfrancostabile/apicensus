package com.accenture.java.apicensus.utils;

/**
 * All routes id.
 *
 * @author Gian F. S.
 */
public enum RouteID {
    ACTIVEMQ_INSERT_PEOPLE("activemq-insert-people"), AUTHENTICATE("authenticate"),
    AUTHENTICATION_MANAGER("authentication-manager"), AUTHENTICATE_POST("authenticate-post"),
    BAD_REQUEST("bad-request"), COUNTRIES_GET("countries-get"), DEFAULT_ROUTE("default-route"),
    DO_INSERT_PEOPLE("do-insert-people"), FILE_PERSON_FTP("file-person-ftp"), INSERT_USER("insert-user"),
    INTERNAL_SERVER_ERROR("internal-server-error"), MISMATCHED_INPUT_FILE("mismatched-input-file"),
    MONGODB_PERSON_FINDONEBY_SSNANDCOUNTRY("mongodb-person-findoneby-ssnandcountry"),
    MONGODB_PERSON_INSERT("mongodb-person-insert"), PERSON_POST("person-post"), REDIRECT_SWAGGER("redirect-swagger"),
    SET_EMPTY_RESPONSELIST("set-empty-response-list"), SWAGGER("swagger"), USER_POST("user-post");

    private String id;

    RouteID(String id) {
        this.id = id;
    }

    public String id() {
        return this.id;
    }
}
