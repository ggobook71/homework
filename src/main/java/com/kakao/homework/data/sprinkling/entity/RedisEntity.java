package com.kakao.homework.data.sprinkling.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/*
RedisEntity
redis
*/
@Getter
@RedisHash(value = "token", timeToLive = 600) //token TTL 10분
public class RedisEntity {
    @Id
    private String assignCode; //this is token >> token : assignCode, userId
    private Integer userId; //뿌린사람Id

    @Builder
    public RedisEntity(String assignCode, Integer userId) {
        this.assignCode = assignCode;
        this.userId = userId;
    }
}
