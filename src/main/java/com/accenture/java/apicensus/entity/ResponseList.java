package com.accenture.java.apicensus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class ResponseList implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Person> successList;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Object> errorList;

    public ResponseList() {
        successList = new ArrayList<>();
        errorList = new ArrayList<>();
    }

    public void addSuccess(Person person) {
        if (person != null) {
            successList.add(person);
        }
    }

    public void addError(Object ssn) {
        errorList.add((ssn == null) ? "null" : ssn);
    }

    public void addError(Object... ssns) {
        if (ssns != null && ssns.length > 0) {
            errorList.addAll(Arrays.asList(ssns));
        }
    }

    @JsonIgnore
    public int getStatusCode() {
        boolean isSuccessEmpty = getSuccessList().isEmpty(),
            isErrorEmpty = getErrorList().isEmpty();

        int statusCode;
        if (!isErrorEmpty) {
            if (isSuccessEmpty) {
                statusCode = 404;
            } else {
                statusCode = 207;
            }
        } else if (!isSuccessEmpty) {
            statusCode = 200;
        } else {
            statusCode = 400;
        }

        return statusCode;
    }
}
