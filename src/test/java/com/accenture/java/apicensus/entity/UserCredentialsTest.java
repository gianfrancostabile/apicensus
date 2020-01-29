package com.accenture.java.apicensus.entity;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class UserCredentialsTest {

    @Test
    public void gettersSetters_AreWellImplemented() {
        assertPojoMethodsFor(UserCredentials.class).testing(Method.GETTER, Method.SETTER)
            .areWellImplemented();
    }
}
