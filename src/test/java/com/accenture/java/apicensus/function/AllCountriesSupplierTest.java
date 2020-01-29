package com.accenture.java.apicensus.function;

import com.accenture.java.apicensus.entity.Country;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class AllCountriesSupplierTest {

    @Test
    public void get_CountryArray_WithoutArguments() {
        assertArrayEquals("AllCountriesSupplier should return a country array", Country.values(),
            new AllCountriesSupplier().get());
    }
}
