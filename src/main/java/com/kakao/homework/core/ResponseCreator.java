package com.kakao.homework.core;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/*
ResponseCreator
처리 응답 매퍼
*/
public class ResponseCreator<T> {
    public Map<String, T> SingleKeyValue(String name, T value) {
        Map<String,T> map = new HashMap<>();
        map.put(name, value);
        return map;
    }
}
