package com.kakao.homework.data.sprinkling.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "token", timeToLive = 600)
public class RedisEntity {
    @Id
    private String assignCode;
    private Integer userId;

    @Builder
    public RedisEntity(String assignCode, Integer userId)
    {
        this.assignCode = assignCode;
        this.userId = userId;
    }
}
