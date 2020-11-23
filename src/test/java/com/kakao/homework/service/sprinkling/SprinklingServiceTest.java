package com.kakao.homework.service.sprinkling;

import com.kakao.homework.core.exception.BusinessException;
import com.kakao.homework.data.sprinkling.dto.SprinklingBodyDto;
import com.kakao.homework.data.sprinkling.dto.SprinklingHeaderDto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SprinklingServiceTest {

    @Autowired
    private SprinklingService sprinklingService;

    /*
    Service Sprinkling Test
    뿌리기 Service 단위 테스트(뿌리기 생성)
    */
    @Test
    void Sprinkling() throws Exception {
        SprinklingHeaderDto sprinklingHeaderDto = new SprinklingHeaderDto();
        SprinklingBodyDto sprinklingBodyDto = new SprinklingBodyDto();
        Map<String, String> result;
        sprinklingHeaderDto.setRoomId("455141");
        sprinklingHeaderDto.setUserId(12334);
        sprinklingBodyDto.setDistMoney(10000);
        sprinklingBodyDto.setReceiveNum(5);
        result = sprinklingService.Sprinkling(sprinklingHeaderDto, sprinklingBodyDto);
        assertTrue(result.get("token").length() == 3);
    }

    /*
    Service Receiving Test
    받기 Service 단위 테스트(받기)
    */
    @Test
    void receiving() throws Exception {
        Map result;
        SprinklingHeaderDto sprinklingHeaderDto = new SprinklingHeaderDto();
        SprinklingBodyDto sprinklingBodyDto = new SprinklingBodyDto();
        sprinklingHeaderDto.setRoomId("455141");
        sprinklingHeaderDto.setUserId(12335);
        String token = "cab";
        result = sprinklingService.Receiving(sprinklingHeaderDto, token);
        assertTrue(result.get("receive_money") != null);
    }

    /*
   Service Searching Test
   받기 Service 단위 테스트(조회하기)
   */
    @Test
    void searching() throws Exception {
        SprinklingHeaderDto sprinklingHeaderDto = new SprinklingHeaderDto();
        Map<String, Object> result = new HashMap<>();
        sprinklingHeaderDto.setRoomId("ABC");
        sprinklingHeaderDto.setUserId(123);
        String token = "a75";
        result = sprinklingService.Searching(sprinklingHeaderDto, token);
        assertFalse(result.isEmpty());
    }

    /*
  Service Receiving Test
  뿌리기 Service 단위 테스트(예외처리)
  */
    @Test
    void sprinkling_뿌리기예외처리테스트() throws Exception {
        SprinklingHeaderDto sprinklingHeaderDto = new SprinklingHeaderDto();
        SprinklingBodyDto sprinklingBodyDto = new SprinklingBodyDto();
        Map<String, String> result;
        sprinklingHeaderDto.setRoomId("455141");
        sprinklingHeaderDto.setUserId(12334);
        sprinklingBodyDto.setDistMoney(0);
        sprinklingBodyDto.setReceiveNum(0);

        BusinessException thrown = assertThrows(
                BusinessException.class,
                () -> sprinklingService.Sprinkling(sprinklingHeaderDto, sprinklingBodyDto),
                "Expected doThing() to throw, but it didn't"
        );
        assertTrue(thrown.getErrorCode().getMessage().contains("금액이나 뿌릴인원이 존재하지 않습니다."));
    }

    /*
    Service Receiving Test
    받기 Service 단위 테스트(예외처리)
    */
    @Test
    void receiving_받기예외처리테스트() throws Exception {
        SprinklingHeaderDto sprinklingHeaderDto = new SprinklingHeaderDto();
        SprinklingBodyDto sprinklingBodyDto = new SprinklingBodyDto();

        sprinklingHeaderDto.setRoomId("ABC");
        sprinklingHeaderDto.setUserId(123);
        String token = "a75";

        BusinessException thrown = assertThrows(
                BusinessException.class,
                () -> sprinklingService.Receiving(sprinklingHeaderDto, token),
                "Expected doThing() to throw, but it didn't"
        );
        assertTrue(thrown.getErrorCode().getMessage().contains("받기 가능시간이 만료 되었습니다.")
                || thrown.getErrorCode().getMessage().contains("받기 대상자가 아닙니다.")
                || thrown.getErrorCode().getMessage().contains("받기는 한번만 가능합니다.")
                || thrown.getErrorCode().getMessage().contains("할당 된 금액을 받기 실패 하였습니다.")
                || thrown.getErrorCode().getMessage().contains("유효 하지 않은 요청입니다."));
    }

    /*
  Service Searching Test
  조회하기 Service 단위 테스트(예외처리)
  */
    @Test
    void searching_조회하기예외처리테스트() throws Exception {
        SprinklingHeaderDto sprinklingHeaderDto = new SprinklingHeaderDto();
        Map<String, Object> result = new HashMap<>();
        sprinklingHeaderDto.setRoomId("ABC");
        sprinklingHeaderDto.setUserId(4412);
        String token = "a75";

        BusinessException thrown = assertThrows(
                BusinessException.class,
                () -> sprinklingService.Searching(sprinklingHeaderDto, token),
                "Expected doThing() to throw, but it didn't"
        );
        assertTrue(thrown.getErrorCode().getMessage().contains("조회 권한이 없거나 기간이 만료 되었습니다."));
    }
}