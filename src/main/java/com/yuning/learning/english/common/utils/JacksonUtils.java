package com.yuning.learning.english.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class JacksonUtils {
    private static final AtomicReference<ObjectMapper> MAPPER = new AtomicReference<>(new ObjectMapper());

    static {
        MAPPER.get().registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String bean2Json(Object obj) {
        try {
            return MAPPER.get().writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
        try {
            return MAPPER.get().readValue(jsonStr, objClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> json2ListBean(String jsonStr, Class<T> objClass)  {
        try {
            return MAPPER.get().readValue(jsonStr, MAPPER.get().getTypeFactory().constructParametricType(List.class, objClass));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String objectToJson(Object data) {
        String string = null;
        try {
            string = MAPPER.get().writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return string;
    }

    public static JsonNode json2Bean(String content) throws IOException {
        return new ObjectMapper().readValue(content, JsonNode.class);
    }

    public static <T> T object2Bean(Object object, Class<T> objClass)
            throws IOException {
        MAPPER.get().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return MAPPER.get().readValue(bean2Json(object), objClass);
    }
}
