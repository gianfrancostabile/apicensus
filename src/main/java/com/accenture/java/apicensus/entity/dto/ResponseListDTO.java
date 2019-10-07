package com.accenture.java.apicensus.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Gian F. S.
 */
@Data
public class ResponseListDTO implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<PersonDTO> successList;

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
        if (personDTO != null) {
            successList.add(personDTO);
        }
    }

    /**
     * Adds the object to the errors list.
     * <br><br>
     * If the value is <b>null</b>, it will change to "null".

     * @param ssn the wrong value for a ssn
     */
    public void addError(Object ssn) {
        errorList.add((ssn == null) ? "null" : ssn);
    }

    /**
     * Adds all objects to the errors list.
     * <br><br>
     * If the list of objects is <b>null</b> or <b>empty</b>,
     * no value will be added to the errors list.
     *
     * @param ssns the list of wrong values for an ssn
     */
    public void addErrors(Object... ssns) {
        if (ssns != null && ssns.length > 0) {
            errorList.addAll(Arrays.asList(ssns));
        }
    }

    /**
     * Returns the appropriate status code, it depends of
     * list values.
     * <br><br>
     *
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
