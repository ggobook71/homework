package com.kakao.homework.controller;

import com.kakao.homework.core.anotation.UserInfoResolver;
import com.kakao.homework.data.sprinkling.dto.SprinklingBodyDto;
import com.kakao.homework.data.sprinkling.dto.SprinklingHeaderDto;
import com.kakao.homework.service.sprinkling.SprinklingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class SprinklingApi {
    private final SprinklingService sprinklingService;

    @PostMapping(path = "/sprinkling")
    public Map RequestSprinklingApi(@UserInfoResolver SprinklingHeaderDto user, @RequestBody SprinklingBodyDto body) throws Exception {
        return sprinklingService.Sprinkling(user, body);
    }

    @GetMapping("/receiving/{token}")
    public Map RequestReceivingApi(@UserInfoResolver SprinklingHeaderDto user, @PathVariable(value = "token") String token) throws Exception {
        return sprinklingService.Receiving(user, token);
    }

    @GetMapping("/searching/{token}")
    public Map RequestSearchingApi(@UserInfoResolver SprinklingHeaderDto user, @PathVariable(value = "token") String token) throws Exception {
        return sprinklingService.Searching(user, token);
    }

}
