package com.accenture.java.apicensus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseList implements Serializable {
    private List<Person> successList;
    private List<Integer> errorList;

    public ResponseList() {
        successList = new ArrayList<>();
        errorList = new ArrayList<>();
    }

    public void addSuccess(Person person) {
        if (person != null) {
            successList.add(person);
        }
    }

    public void addError(Integer ssn) {
        if (ssn != null) {
            errorList.add(ssn);
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
