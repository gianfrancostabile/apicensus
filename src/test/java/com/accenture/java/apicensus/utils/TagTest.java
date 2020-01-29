package com.accenture.java.apicensus.utils;

import org.junit.Assert;
import org.junit.Test;

public class TagTest {

    @Test
    public void tag_Get_WithoutArguments() {
        Assert.assertEquals("Tag.GET", "GET", Tag.GET.name());
    }

    @Test
    public void tag_Post_WithoutArguments() {
        Assert.assertEquals("Tag.POST", "POST", Tag.POST.name());
    }
}
