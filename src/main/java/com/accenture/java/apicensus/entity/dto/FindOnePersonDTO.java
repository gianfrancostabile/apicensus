package com.accenture.java.apicensus.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Gian F. S.
 */
@Getter
@Setter
@Builder
public class FindOnePersonDTO implements Serializable {

    @NotNull
    private Long ssn;

    @NotBlank
    private String country;
}
