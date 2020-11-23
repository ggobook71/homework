package com.kakao.homework.data.sprinkling.entity;

import com.kakao.homework.repository.RedisCrudRepository;
import com.kakao.homework.repository.sprinkling.DistMoneyRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


/*
Repository Stored Searching
데이터 조회 관련 단위 테스트(redis)
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class RedisEntityTest {
    @Autowired
    private RedisCrudRepository redisCrudRepository;
    Optional<RedisEntity> redisEntity = redisCrudRepository.findById("a63");

    @Test
    void getAssignCode() {
        assertTrue(redisEntity.isPresent());
    }

    @Test
    void getUserId() {
        assertTrue(redisEntity.get().getUserId() > 0);
    }
}