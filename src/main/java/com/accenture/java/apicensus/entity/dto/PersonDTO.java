package com.accenture.java.apicensus.entity.dto;

import com.accenture.java.apicensus.entity.Country;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Gian F. S.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO implements Serializable {

    @NotNull
    private Integer ssn;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate bornDate;

    @NotNull
    private Country country;

    @NotNull
    private String genre;
}
