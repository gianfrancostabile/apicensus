package com.accenture.java.apicensus.function;

import com.accenture.java.apicensus.entity.Country;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * @author Gian F. S.
 */
@Component
public class AllCountriesSupplier implements Supplier<Country[]> {

    /**
     * Returns all countries from {@link Country} enum.
     *
     * @return all countries
     *
     * @see Country
     */
    @Override
    public Country[] get() {
        return Country.values();
    }
}
