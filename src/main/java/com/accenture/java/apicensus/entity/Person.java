package com.accenture.java.apicensus.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable {

    @JsonProperty("ssn")
    private Integer ssn;

    @JsonProperty("name")
    private String name;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("bornDate")
    private LocalDateTime bornDate;

    @JsonProperty("country")
    private Country country;

    @JsonProperty("genre")
    private String genre;
}
