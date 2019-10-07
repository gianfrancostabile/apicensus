package com.accenture.java.apicensus.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Gian F. S.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindOnePersonDTO implements Serializable {
    private Integer ssn;
    private String country;
}
