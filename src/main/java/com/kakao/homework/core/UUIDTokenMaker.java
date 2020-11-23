package com.kakao.homework.core;

import java.util.UUID;

public class UUIDTokenMaker {
    public String getNewToken() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, 3);
    }
}
