package com.kakao.homework.repository;

import com.kakao.homework.data.sprinkling.entity.RedisEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/*
   repository
   Redis
 */
public interface RedisCrudRepository extends CrudRepository<RedisEntity, String> {
    Optional<RedisEntity> findById(String assignCode);
}
