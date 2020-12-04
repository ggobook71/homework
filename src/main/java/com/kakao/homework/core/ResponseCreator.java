package com.kakao.homework.core;

import java.util.HashMap;
import java.util.Map;

/*
ResponseCreator
처리 응답 매퍼
*/
public class ResponseCreator<T, S> {
    public Map<T, S> SingleKeyValue(T name, S value) {
        Map<T, S> map = new HashMap<>();
        map.put(name, value);
        return map;
    }
}
