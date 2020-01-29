package com.accenture.java.apicensus.entity.dto;

import com.accenture.java.apicensus.entity.Country;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Gian F. S.
 */
@Getter
@Setter
@Builder
@ApiModel(description = "Represents a person of the system")
@JsonDeserialize(builder = PersonDTO.PersonDTOBuilder.class)
public class PersonDTO implements Serializable {

    @NotNull
    @Min(1000000)
    @Max(999999999)
    private Long ssn;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate bornDate;

    @NotNull
    private Country country;

    @NotBlank
    private String genre;

    @JsonPOJOBuilder(withPrefix = "")
    public static class PersonDTOBuilder {

    }
}
