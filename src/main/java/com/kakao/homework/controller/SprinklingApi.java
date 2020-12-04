package com.kakao.homework.controller;

import com.kakao.homework.core.anotation.UserInfoResolver;
import com.kakao.homework.data.sprinkling.dto.SprinklingBodyDto;
import com.kakao.homework.data.sprinkling.dto.SprinklingHeaderDto;
import com.kakao.homework.service.sprinkling.SprinklingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/*
SprinklingApi
Controller
*/
@RestController
@RequestMapping(value = "/api")
public class SprinklingApi {
    /*
    SprinklingService
    뿌리기
    */
    private final SprinklingService sprinklingService;

    @Autowired
    public SprinklingApi(SprinklingService sprinklingService) {
        this.sprinklingService = sprinklingService;
    }

    @PostMapping(path = "/sprinkling")
    public Map<String, String> requestSprinklingApi(@UserInfoResolver SprinklingHeaderDto user, @RequestBody SprinklingBodyDto body) {
        return sprinklingService.Sprinkling(user, body);
    }

    /*
    requestReceivingApi
    받기
    */
    @GetMapping("/receiving/{token}")
    public Map<String, Integer> requestReceivingApi(@UserInfoResolver SprinklingHeaderDto user, @PathVariable(value = "token") String token) throws Exception {
        return sprinklingService.Receiving(user, token);
    }

    /*
    requestSearchingApi
    조회하기
    */
    @GetMapping("/searching/{token}")
    public Map<String, Object> requestSearchingApi(@UserInfoResolver SprinklingHeaderDto user, @PathVariable(value = "token") String token) {
        return sprinklingService.Searching(user, token);
    }
}
