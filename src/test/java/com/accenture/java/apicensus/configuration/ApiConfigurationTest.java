package com.accenture.java.apicensus.configuration;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ApiConfigurationTest {

    private ApiConfiguration apiConfiguration;

    @Before
    public void setUp() {
        this.apiConfiguration = new ApiConfiguration();
    }

    @Test
    public void objectMapper_ObjectMapper_WithoutArguments() {
        assertNotNull("Object mapper should not be null", this.apiConfiguration.objectMapper());
    }

    @Test
    public void jsonWriterSettings_JsonWriterSettingsObject_WithoutArguments() {
        assertNotNull("JSON writer settings should not be null", this.apiConfiguration.jsonWriterSettings());
    }
}
