package com.accenture.java.apicensus.entity;

/**
 * Enum with a reference to all created endpoints.
 * <br><br>
 * Each value is sorted alphabetically.
 *
 * @author Gian F. S.
 */
public enum Endpoint {
    DIRECT_BAD_REQUEST("direct:badRequest"),
    DIRECT_FINDONEBY_SSN_AND_COUNTRY("direct:findOneBySsnAndCountry"),
    DIRECT_INSERT_DB_PERSON("direct:insertDBPerson"),
    DIRECT_INTERNAL_SERVER_ERROR("direct:internalServerError"),
    DIRECT_SET_SAME_BODY("direct:setSameBody"),

    DIRECT_DEFAULT_ENDPOINT("direct:defaultEndpoint");

    private String endpoint;

    private Endpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String endpoint() {
        return endpoint;
    }
}
