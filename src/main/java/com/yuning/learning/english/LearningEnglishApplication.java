package com.yuning.learning.english;

import com.yuning.learning.english.config.FeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@ServletComponentScan(value = {"com.yuning.learning.english"})
@EnableFeignClients(value = {"com.yuning.learning.english.feign"}, defaultConfiguration = FeignConfig.class)
@MapperScan("com.yuning.learning.english.mapper")
public class LearningEnglishApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearningEnglishApplication.class, args);
    }

}
