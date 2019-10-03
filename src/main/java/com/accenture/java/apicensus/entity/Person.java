package com.accenture.java.apicensus.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "persons")
@CompoundIndex(def = "{'ssn':1, 'country':1}", name = "unqSsnCountry")
public class Person implements Serializable {

    @NotNull
    private Integer ssn;

    @NotNull @NotBlank
    private String name;

    @NotNull @NotBlank
    private String surname;

    @NotNull
    private LocalDateTime bornDate;

    @NotNull
    private Country country;

    @NotNull @NotBlank
    private String genre;
}
