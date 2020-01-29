package com.accenture.java.apicensus.entity.dto;

import com.accenture.java.apicensus.entity.Country;
import org.junit.Before;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class ResponseListDTOTest {

    private ResponseListDTO responseListDTO;
    private List<PersonDTO> successList;
    private List<Long> errorList;
    private List<PersonDTO> emptyPersonList;
    private List<Long> emptyLongList;

    @Before
    public void setUp() {
        this.responseListDTO = new ResponseListDTO();
        this.responseListDTO.setSuccessList(null);
        this.responseListDTO.setErrorList(null);

        this.successList = new ArrayList<>();
        this.errorList = new ArrayList<>();

        this.emptyPersonList = new ArrayList<>();
        this.emptyLongList = new ArrayList<>();
    }

    @Test
    public void gettersSetters_AreWellImplemented() {
        assertPojoMethodsFor(ResponseListDTO.class).testing(Method.GETTER, Method.SETTER)
            .areWellImplemented();
    }

    @Test
    public void addSuccess_AllValuesAdded_IfSuccessListIsEmptyAndGivenListNotNullAndNotEmpty() {
        this.successList.add(PersonDTO.builder()
            .ssn(11111111L)
            .name("Luis")
            .surname("Sena")
            .bornDate(LocalDate.of(2000, 5, 9))
            .country(Country.BR)
            .genre("Male")
            .build());
        this.successList.add(PersonDTO.builder()
            .ssn(11111112L)
            .name("Martha")
            .surname("Juri")
            .bornDate(LocalDate.of(1977, 9, 22))
            .country(Country.UY)
            .genre("Female")
            .build());

        this.responseListDTO.addSuccess(this.successList);
        assertEquals("List should be equals", this.successList, this.responseListDTO.getSuccessList());
    }

    @Test
    public void addSuccess_AllValuesAdded_IfSuccessListNotEmptyAndGivenListNotNullAndNotEmpty() {
        this.successList.add(PersonDTO.builder()
            .ssn(11111111L)
            .name("Luis")
            .surname("Sena")
            .bornDate(LocalDate.of(2000, 5, 9))
            .country(Country.BR)
            .genre("Male")
            .build());
        this.successList.add(PersonDTO.builder()
            .ssn(11111112L)
            .name("Martha")
            .surname("Juri")
            .bornDate(LocalDate.of(1977, 9, 22))
            .country(Country.UY)
            .genre("Female")
            .build());
        List<PersonDTO> expectedList = new ArrayList<>(this.successList);

        this.responseListDTO.addSuccess(this.successList);

        this.successList.clear();
        this.successList.add(PersonDTO.builder()
            .ssn(11111113L)
            .name("Sadio")
            .surname("Mane")
            .bornDate(LocalDate.of(1992, 11, 21))
            .country(Country.VE)
            .genre("Male")
            .build());
        expectedList.addAll(this.successList);

        this.responseListDTO.addSuccess(this.successList);
        assertEquals("List should be equals", expectedList, this.responseListDTO.getSuccessList());
    }

    @Test
    public void addSuccess_NoValuesAdded_IfGivenListIsNull() {
        this.responseListDTO.addSuccess(null);
        assertEquals("List should be equals", this.emptyPersonList, this.responseListDTO.getSuccessList());
    }

    @Test
    public void addSuccess_NoValuesAdded_IfGivenListNotNullAndIsEmpty() {
        this.responseListDTO.addSuccess(new ArrayList<>());
        assertEquals("List should be equals", this.emptyPersonList, this.responseListDTO.getSuccessList());
    }

    @Test
    public void addErrors_AllValuesAdded_IfErrorListIsEmptyAndGivenListNotNullAndNotEmpty() {
        this.errorList = new ArrayList<>();
        this.errorList.add(1111L);
        this.errorList.add(33123L);
        this.errorList.add(998L);
        this.errorList.add(768767868L);

        this.responseListDTO.addErrors(this.errorList);
        assertEquals("List should be equals", this.errorList, this.responseListDTO.getErrorList());
    }

    @Test
    public void addErrors_AllValuesAdded_IfErrorListNotEmptyAndGivenListNotNullAndNotEmpty() {
        this.errorList.add(1111L);
        this.errorList.add(33123L);
        this.errorList.add(998L);
        this.errorList.add(768767868L);
        List<Long> expectedList = new ArrayList<>(this.errorList);

        this.responseListDTO.addErrors(this.errorList);

        this.errorList.clear();
        this.errorList.add(33232L);
        this.errorList.add(8787887L);
        expectedList.addAll(this.errorList);

        this.responseListDTO.addErrors(this.errorList);
        assertEquals("List should be equals", expectedList, this.responseListDTO.getErrorList());
    }

    @Test
    public void addErrors_NoValuesAdded_IfGivenListIsNull() {
        this.responseListDTO.addErrors(null);
        assertEquals("List should be equals", new ArrayList(), this.responseListDTO.getErrorList());
    }

    @Test
    public void addErrors_NoValuesAdded_IfGivenListNotNullAndIsEmpty() {
        this.responseListDTO.addErrors(new ArrayList<>());
        assertEquals("List should be equals", new ArrayList(), this.responseListDTO.getErrorList());
    }

    @Test
    public void getStatusCode_400_IfSuccessListIsNullAndErrorListIsNull() {
        assertEquals("Null list, status code should be 400", 400, this.responseListDTO.getStatusCode());
    }

    @Test
    public void getStatusCode_400_IfSuccessListIsEmptyAndErrorListIsEmpty() {
        this.responseListDTO.setSuccessList(this.successList);
        this.responseListDTO.setErrorList(this.errorList);
        assertEquals("Empty list, status code should be 400", 400, this.responseListDTO.getStatusCode());
    }

    @Test
    public void getStatusCode_200_IfSuccessListHasValuesAndErrorListIsEmpty() {
        this.successList.add(PersonDTO.builder()
            .ssn(1234556645L)
            .name("Carlos")
            .surname("Martinez")
            .country(Country.BO)
            .bornDate(LocalDate.of(1930, 2, 23))
            .genre("Male")
            .build());

        this.responseListDTO.setSuccessList(this.successList);
        this.responseListDTO.setErrorList(this.errorList);

        assertEquals("Success list with values and error list empty, status code should be 200", 200,
            this.responseListDTO.getStatusCode());
    }

    @Test
    public void getStatusCode_404_IfSuccessListIsEmptyAndErrorListHasValues() {
        this.errorList.add(3353675L);

        this.responseListDTO.setSuccessList(this.successList);
        this.responseListDTO.setErrorList(this.errorList);

        assertEquals("Success list empty and error list with values, status code should be 404", 404,
            this.responseListDTO.getStatusCode());
    }

    @Test
    public void getStatusCode_207_IfSuccessListHasValuesAndErrorListHasValues() {
        this.successList.add(PersonDTO.builder()
            .ssn(1234556645L)
            .name("Carlos")
            .surname("Martinez")
            .country(Country.BO)
            .bornDate(LocalDate.of(1930, 2, 23))
            .genre("Male")
            .build());
        this.errorList.add(3353675L);

        this.responseListDTO.setSuccessList(this.successList);
        this.responseListDTO.setErrorList(this.errorList);

        assertEquals("Success list with values and error list with values, status code should be 207", 207,
            this.responseListDTO.getStatusCode());
    }
}
