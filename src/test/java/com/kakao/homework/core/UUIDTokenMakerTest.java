package com.kakao.homework.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/*
 token making
 토큰발급 기능 단위테스트
 */
@SpringBootTest
class UUIDTokenMakerTest {
    UUIDTokenMaker uuidTokenMaker = new UUIDTokenMaker();

    @Test
    public void UUID3자리_발급() {
        Assertions.assertTrue(uuidTokenMaker.getNewToken().length() == 3);
    }
}