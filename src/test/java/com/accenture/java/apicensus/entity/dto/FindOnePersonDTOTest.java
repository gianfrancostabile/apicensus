package com.accenture.java.apicensus.entity.dto;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class FindOnePersonDTOTest {

    @Test
    public void gettersSetters_AreWellImplemented() {
        assertPojoMethodsFor(FindOnePersonDTO.class).testing(Method.GETTER, Method.SETTER)
            .areWellImplemented();
    }
}
