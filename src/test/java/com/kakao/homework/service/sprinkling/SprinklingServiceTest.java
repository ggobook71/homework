package com.kakao.homework.service.sprinkling;

import com.kakao.homework.data.sprinkling.dto.SprinklingBodyDto;
import com.kakao.homework.data.sprinkling.dto.SprinklingHeaderDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SprinklingServiceTest {

    @Autowired
    private SprinklingService sprinklingService;

    private SprinklingHeaderDto sprinklingHeaderDto = new SprinklingHeaderDto();
    //private SprinklingBodyDto.Sprinkling sprinklingApiDto = new SprinklingBodyDto.Sprinkling();
    //private SprinklingBodyDto.Receiver receiverApiDto = new SprinklingBodyDto.Receiver();

    @Test
    void sprinkling() throws Exception {
        sprinklingHeaderDto.setRoomId("abc");
        sprinklingHeaderDto.setUserId(123);
        //sprinklingApiDto.setDistMoney(5000);
        //sprinklingApiDto.setReceiveNum(5);
        //String result = sprinklingService.Sprinkling(sprinklingHeaderDto, sprinklingApiDto);

        //assertTrue(result.length() == 3);
    }

    @Test
    void receiver() throws Exception{
        //sprinklingHeaderDto.setRoomId("abc");
        //sprinklingHeaderDto.setUserId(123);
        //receiverApiDto.setToken("bae");
        //String result = sprinklingService.Receiver(sprinklingHeaderDto, receiverApiDto.getToken());
        //assertTrue(result.length() != 2);
    }

}