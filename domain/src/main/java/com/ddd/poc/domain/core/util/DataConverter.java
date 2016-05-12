package com.ddd.poc.domain.core.util;

import com.ddd.poc.domain.core.ex.DataConversionRuntimeException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DataConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataConverter.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MAPPER.configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, false);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String toString(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            throw new DataConversionRuntimeException(ex);
        }
    }

    public static <T> T toObject(String data, Class<T> dataClass) {
        try {
            return MAPPER.readValue(data, dataClass);
        } catch (IOException ex) {
            throw new DataConversionRuntimeException(ex);
        }
    }
}
