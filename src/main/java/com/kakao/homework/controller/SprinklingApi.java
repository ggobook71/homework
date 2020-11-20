package com.kakao.homework.controller;

import com.kakao.homework.core.UUIDTokenMaker;
import com.kakao.homework.core.anotation.UserInfoResolver;
import com.kakao.homework.data.sprinkling.dto.SprinklingApiDto;
import com.kakao.homework.data.sprinkling.dto.UserInfoDto;
import com.kakao.homework.service.sprinkling.SprinklingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/Api")
public class SprinklingApi {
    private final SprinklingService sprinklingService;
    private final UUIDTokenMaker uuid;

    @GetMapping(path="/Sprinkling")
    public String RequestSprinklingApi(@UserInfoResolver UserInfoDto user, @RequestBody SprinklingApiDto.Sprinkling body)
    {
        return sprinklingService.Sprinkling(user, body);
    }

    @GetMapping("/test")
    public String RequestTest()
    {
        return uuid.getNewToken();
    }
}