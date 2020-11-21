package com.kakao.homework.core;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;

@RequiredArgsConstructor
public class CacheCreator {
    private final RedissonClient redissonClient;
    public boolean CreateKey(String key) throws Exception
    {



        return false;
    }
}
