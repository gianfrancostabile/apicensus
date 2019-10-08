package com.accenture.java.apicensus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "people")
@CompoundIndex(name = "unqSsnCountry", def = "{'ssn':1, 'country':1}", unique = true)
public class Person {
    private Integer ssn;
    private String name;
    private String surname;
    private String bornDate;
    private String country;
    private String genre;
}
