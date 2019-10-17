package com.accenture.java.apicensus.entity.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Gian F. S.
 */
@Data
@Builder
public class FindOnePersonDTO implements Serializable {

    @NotNull
    private Integer ssn;

    @NotNull
    private String country;
}
