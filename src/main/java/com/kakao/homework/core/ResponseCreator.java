package com.kakao.homework.core;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResponseCreator {
    public Map SingleKeyValueString(String name, String value) {
        Map map = new HashMap();
        map.put(name, value);
        return map;
    }

    public Map SingleKeyValueInt(String name, int value) {
        Map map = new HashMap();
        map.put(name, value);
        return map;
    }
}
