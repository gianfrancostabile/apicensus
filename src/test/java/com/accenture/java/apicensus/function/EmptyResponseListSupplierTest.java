package com.accenture.java.apicensus.function;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class EmptyResponseListSupplierTest {

    private EmptyResponseListSupplier emptyResponseListSupplier;

    @Before
    public void setUp() {
        this.emptyResponseListSupplier = new EmptyResponseListSupplier();
    }

    @Test
    public void get_ResponseList_WithoutArguments() {
        assertNotNull("ResponseList not null", this.emptyResponseListSupplier.get());
    }

    @Test
    public void get_ResponseListNotNullSuccessList_WithoutArguments() {
        assertNotNull("Success list should not be null", this.emptyResponseListSupplier.get()
            .getSuccessList());
    }

    @Test
    public void get_ResponseListEmptySuccessList_WithoutArguments() {
        assertTrue("Success list should be empty", this.emptyResponseListSupplier.get()
            .getSuccessList()
            .isEmpty());
    }

    @Test
    public void get_ResponseListNotNullErrorList_WithoutArguments() {
        assertNotNull("Error list should not be null", this.emptyResponseListSupplier.get()
            .getErrorList());
    }

    @Test
    public void get_ResponseListEmptyErrorList_WithoutArguments() {
        assertTrue("Error list should be empty", this.emptyResponseListSupplier.get()
            .getErrorList()
            .isEmpty());
    }
}
