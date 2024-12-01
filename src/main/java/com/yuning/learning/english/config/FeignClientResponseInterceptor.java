package com.yuning.learning.english.config;

import com.alibaba.fastjson2.JSON;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.jackson.JacksonDecoder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author lingjia
 * @date 2024/2/26
 */
@Slf4j
public class FeignClientResponseInterceptor extends SpringDecoder {
    public FeignClientResponseInterceptor(ObjectFactory<HttpMessageConverters> messageConverters) {
        super(messageConverters);
    }

    @SneakyThrows
    @Override
    public Object decode(Response response, Type type) throws FeignException {
        if (response.body() != null) {
            byte[] bodyData = Util.toByteArray(response.body().asInputStream());
            int bodyLength = bodyData.length;
            if (bodyLength > 0) {
                response = response.toBuilder().body(bodyData).build();
            }
        }

        Object resp = null;
        if (response.status() == HttpStatus.OK.value() || response.status() == HttpStatus.CREATED.value()
                || response.status() == HttpStatus.ACCEPTED.value()) {
            String bodyStr = IOUtils.toString(response.body().asReader(StandardCharsets.UTF_8));
            resp = JSON.parseObject(bodyStr);
        }


        String body = Objects.isNull(resp) ? StringUtils.EMPTY : resp.toString();
        Response build = response.toBuilder().body(body, StandardCharsets.UTF_8).build();
        return super.decode(build, type);
    }
}
