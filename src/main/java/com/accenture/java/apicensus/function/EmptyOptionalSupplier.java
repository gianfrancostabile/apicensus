package com.accenture.java.apicensus.function;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author Gian F. S.
 */
@Component
public class EmptyOptionalSupplier implements Supplier<Optional> {

    /**
     * Returns an empty optional.
     *
     * @return and empty optional
     */
    @Override
    public Optional get() {
        return Optional.empty();
    }
}
