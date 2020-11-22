package com.kakao.homework.service.sprinkling;

import com.google.gson.annotations.SerializedName;
import com.kakao.homework.controller.SprinklingApi;
import com.kakao.homework.data.sprinkling.dto.SprinklingApiDto;
import com.kakao.homework.data.sprinkling.dto.UserInfoDto;
import com.kakao.homework.data.sprinkling.entity.CacheEntity;
import com.kakao.homework.data.sprinkling.entity.DistMoney;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SprinklingServiceTest {

    @Autowired
    private SprinklingService sprinklingService;

    private UserInfoDto userInfoDto = new UserInfoDto();
    private SprinklingApiDto.Sprinkling sprinklingApiDto = new SprinklingApiDto.Sprinkling();
    private SprinklingApiDto.Receiver receiverApiDto = new SprinklingApiDto.Receiver();

    @Test
    void sprinkling() throws Exception {
        userInfoDto.setRoomId("abc");
        userInfoDto.setUserId(123);
        sprinklingApiDto.setDistMoney(5000);
        sprinklingApiDto.setReceiveNum(5);
        String result = sprinklingService.Sprinkling(userInfoDto, sprinklingApiDto);

        assertTrue(result.length() == 3);
    }

    @Test
    void receiver() throws Exception{
        userInfoDto.setRoomId("abc");
        userInfoDto.setUserId(123);
        receiverApiDto.setToken("bae");
        String result = sprinklingService.Receiver(userInfoDto, receiverApiDto.getToken());
        assertTrue(result.length() != 2);
    }

}