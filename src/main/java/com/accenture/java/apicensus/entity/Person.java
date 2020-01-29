package com.accenture.java.apicensus.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Gian F. S.
 */
@JsonDeserialize(builder = Person.PersonBuilder.class)
@Builder
@Getter
@Setter
@Document(value = "people")
@CompoundIndex(name = "unqSsnCountry", def = "{'ssn':1, 'country':1}", unique = true)
public class Person implements Serializable {

    @NotNull
    @Min(1000000)
    @Max(999999999)
    private Long ssn;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private String bornDate;

    @NotNull
    @Size(min = 2, max = 2)
    private String country;

    @NotBlank
    private String genre;

    @JsonPOJOBuilder(withPrefix = "")
    public static class PersonBuilder {

    }
}

