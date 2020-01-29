package com.accenture.java.apicensus.function;

import com.accenture.java.apicensus.entity.dto.ResponseListDTO;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * @author Gian F. S.
 */
@Component
public class EmptyResponseListSupplier implements Supplier<ResponseListDTO> {

    /**
     * Returns a {@link ResponseListDTO} instance.
     *
     * @return the {@link ResponseListDTO} instance
     * @see ResponseListDTO
     */
    @Override
    public ResponseListDTO get() {
        return new ResponseListDTO();
    }
}
