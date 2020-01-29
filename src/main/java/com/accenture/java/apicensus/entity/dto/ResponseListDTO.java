package com.accenture.java.apicensus.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Gian F. S.
 */
@Getter
@Setter
@ApiModel(description = "Represents the response after the post persons request")
public class ResponseListDTO implements Serializable {

    @ApiModelProperty(value = "List of persons successfully processed")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<PersonDTO> successList;

    @ApiModelProperty(value = "List of ssn wrongly processed")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Long> errorList;

    public ResponseListDTO() {
        this.initializeListIfNull();
    }

    /**
     * Initializes the success and error list
     * if they are {@code null}.
     *
     * @see Objects
     * @see ArrayList
     */
    private void initializeListIfNull() {
        if (Objects.isNull(this.successList)) {
            this.setSuccessList(new ArrayList<>());
        }
        if (Objects.isNull(this.errorList)) {
            this.setErrorList(new ArrayList<>());
        }
    }

    /**
     * Adds the people to the success list.
     * <br><br>
     * If the success list is {@code null}, it will be initialized.<br>
     * If the list of people is {@code null} or <b>empty</b>,
     * no value will be added to the errors list.
     *
     * @param peopleList the list of wrong values for an ssn
     * @see List
     * @see PersonDTO
     * @see #initializeListIfNull()
     * @see Objects
     */
    public void addSuccess(List<PersonDTO> peopleList) {
        this.initializeListIfNull();
        if (Objects.nonNull(peopleList) && !peopleList.isEmpty()) {
            this.successList.addAll(peopleList);
        }
    }

    /**
     * Adds all objects to the errors list.
     * <br><br>
     * If the error list is {@code null}, it will be initialized.<br>
     * If the list of objects is {@code null} or <b>empty</b>,
     * no value will be added to the errors list.
     *
     * @param ssnList the list of wrong values for an ssn
     * @see List
     * @see #initializeListIfNull()
     * @see Objects
     */
    public void addErrors(List<Long> ssnList) {
        this.initializeListIfNull();
        if (Objects.nonNull(ssnList) && !ssnList.isEmpty()) {
            this.errorList.addAll(ssnList);
        }
    }

    /**
     * Returns the appropriate status code, it depends of
     * list values.
     * <br><br>
     * <p>
     * If success list and error list are {@code null}, will be initialized.
     * </p>
     * <p>
     * List of possible status codes (SL -> Success List; EL -> Error List):
     * <ul>
     *      <li>
     *          <b>200:</b> <i>SL</i> has values and <i>EL</i> has no values
     *      </li>
     *      <li>
     *          <b>207:</b> <i>SL</i> has values and <i>EL</i> has values
     *      </li>
     *      <li>
     *          <b>400:</b> <i>SL</i> has no values and <i>EL</i> has no values
     *      </li>
     *      <li>
     *          <b>404:</b> <i>SL</i> has no values and <i>EL</i> has values
     *      </li>
     * </ul>
     *
     * @return the status code
     */
    @JsonIgnore
    public int getStatusCode() {
        this.initializeListIfNull();
        boolean havePeople = !this.getSuccessList()
            .isEmpty();
        boolean haveError = !this.getErrorList()
            .isEmpty();
        int statusCode = 400;
        if (havePeople) {
            statusCode = haveError ? 207 : 200;
        } else if (haveError) {
            statusCode = 404;
        }
        return statusCode;
    }
}
