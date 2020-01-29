package com.accenture.java.apicensus.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CountryTest {

    @Test
    public void name_AR_WithoutArguments() {
        assertEquals("Country.AR should return AR", "AR", Country.AR.name());
    }

    @Test
    public void name_BO_WithoutArguments() {
        assertEquals("Country.BO should return BO", "BO", Country.BO.name());
    }

    @Test
    public void name_BR_WithoutArguments() {
        assertEquals("Country.BR should return BR", "BR", Country.BR.name());
    }

    @Test
    public void name_PY_WithoutArguments() {
        assertEquals("Country.PY should return PY", "PY", Country.PY.name());
    }

    @Test
    public void name_UY_WithoutArguments() {
        assertEquals("Country.UY should return UY", "UY", Country.UY.name());
    }

    @Test
    public void name_VE_WithoutArguments() {
        assertEquals("Country.VE should return VE", "VE", Country.VE.name());
    }
}
