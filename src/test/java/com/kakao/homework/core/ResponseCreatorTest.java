package com.kakao.homework.core;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/*
 response mapping value
 응답할 데이터 타입 변환기능 단위 테스트
 */
class ResponseCreatorTest {
    ResponseCreator responseCreator = new ResponseCreator();

    @Test
    void singleKeyValueString() {
        Map<String, String> map = responseCreator.SingleKeyValueString("333", "aaa");
        assertTrue(map.get("333").equals("aaa"));
    }

    @Test
    void singleKeyValueInt() {
        Map<String, Integer> map = responseCreator.SingleKeyValueInt("2", 0);
        assertTrue(map.get("2") == 0);
    }
}