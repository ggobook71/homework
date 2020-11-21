package com.kakao.homework.core;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDTokenMaker {
    public String getNewToken()
    {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0,3);
    }
}
