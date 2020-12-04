package com.kakao.homework.core;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/*
ResponseCreator
처리 응답 매퍼
*/
@Component
public class ResponseCreator {
    public Map<String, String> SingleKeyValue(String name, String value) {
        Map<String,String> map = new HashMap<>();
        map.put(name, value);
        return map;
    }
    public Map<String, Integer>  SingleKeyValue(String name, Integer value) {
        Map<String, Integer> map = new HashMap<>();
        map.put(name, value);
        return map;
    }
}
