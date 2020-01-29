package com.accenture.java.apicensus.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.bson.json.JsonWriterSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class to create beans.
 *
 * @author Gian F. S.
 */
@Configuration
public class ApiConfiguration {

    /**
     * Creates a ObjectMapper bean.
     *
     * @return a ObjectMapper instance
     */
    @Bean("objectMapper")
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModules(new Jdk8Module(), new JavaTimeModule());
    }

    /**
     * Creates a JsonWriterSettings bean.
     *
     * @return a JsonWriterSettings instance
     */
    @Bean("jsonWriterSettings")
    public JsonWriterSettings jsonWriterSettings() {
        return JsonWriterSettings.builder()
            .int64Converter((value, writer) -> writer.writeNumber(value.toString()))
            .build();
    }
}
