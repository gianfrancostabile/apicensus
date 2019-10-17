package com.accenture.java.apicensus.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonDeserialize(builder = Person.PersonBuilder.class)
@Builder
@Getter
@EqualsAndHashCode
@Document(value = "people")
@CompoundIndex(name = "unqSsnCountry", def = "{'ssn':1, 'country':1}", unique = true)
@ApiModel(value = "Represents a person of the system")
public class Person {

    @NotNull
    @Min(1000000)
    @Max(999999999)
    private Integer ssn;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String bornDate;

    @NotNull
    @Size(min = 2, max = 2)
    private String country;

    @NotNull
    private String genre;

    @JsonPOJOBuilder(withPrefix = "")
    public static class PersonBuilder {

    }
}

