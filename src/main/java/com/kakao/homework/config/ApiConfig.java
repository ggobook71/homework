package com.kakao.homework.config;

import com.kakao.homework.core.Resolver.UserInfoArgResolver;
import com.kakao.homework.data.sprinkling.dto.UserInfoDto;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ApiConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new UserInfoArgResolver());
    }
}
