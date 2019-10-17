package com.accenture.java.apicensus.entity.dto;

import com.accenture.java.apicensus.entity.Country;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Gian F. S.
 */
@Data
@Builder
@ApiModel(description = "Represents a person of the system")
public class PersonDTO implements Serializable {

    @NotNull
    @Min(1000000)
    @Max(999999999)
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
