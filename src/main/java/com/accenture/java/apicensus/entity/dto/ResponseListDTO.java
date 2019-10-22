package com.accenture.java.apicensus.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Gian F. S.
 */
@Data
@ApiModel(description = "Represents the response after the post persons request")
public class ResponseListDTO implements Serializable {

    @ApiModelProperty(value = "List of persons successfully processed")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<PersonDTO> successList;

    @ApiModelProperty(value = "List of ssns wrongly processed")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Object> errorList;

    public ResponseListDTO() {
        successList = new ArrayList<>();
        errorList = new ArrayList<>();
    }

    /**
     * Adds a person to the success list
     * <br><br>
     * If the value is <b>null</b> it won't be added.
     *
     * @param personDTO the person instance to add
     */
    public void addSuccess(PersonDTO personDTO) {
        if (Objects.nonNull(personDTO)) {
            successList.add(personDTO);
        }
    }

    /**
     * Adds the people to the success list.
     * <br><br>
     * If the list of people is {@code null} or <b>empty</b>,
     * no value will be added to the errors list.
     *
     * @param peopleList the list of wrong values for an ssn
     */
    public void addSuccess(List<PersonDTO> peopleList) {
        if (Objects.nonNull(peopleList) && !peopleList.isEmpty()) {
            successList.addAll(peopleList);
        }
    }

    /**
     * Adds the object to the errors list.
     * <br><br>
     * If the value is <b>null</b>, it will change to "null".
     *
     * @param ssn the wrong value for a ssn
     */
    public void addError(Object ssn) {
        errorList.add(Objects.nonNull(ssn) ? ssn : "null");
    }

    /**
     * Adds all objects to the errors list.
     * <br><br>
     * If the list of objects is {@code null} or <b>empty</b>,
     * no value will be added to the errors list.
     *
     * @param ssnList the list of wrong values for an ssn
     */
    public void addErrors(List<Object> ssnList) {
        if (Objects.nonNull(ssnList) && !ssnList.isEmpty()) {
            errorList.addAll(ssnList);
        }
    }

    /**
     * Returns the appropriate status code, it depends of
     * list values.
     * <br><br>
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
        boolean isSuccessEmpty = getSuccessList().isEmpty(), isErrorEmpty = getErrorList().isEmpty();

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
