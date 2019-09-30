package com.accenture.java.apicensus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO implements Serializable {
    private Integer ssn;
    private Country country;
}
