package com.kakao.homework.repository;

import com.kakao.homework.data.sprinkling.entity.CacheEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RedisCrudRepo extends CrudRepository<CacheEntity, String> {
    Optional<CacheEntity> findById(String assignCode);
}
