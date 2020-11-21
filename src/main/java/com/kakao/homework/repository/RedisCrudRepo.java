package com.kakao.homework.repository;

import com.kakao.homework.data.sprinkling.entity.CacheEntity;
import org.springframework.data.repository.CrudRepository;

public interface RedisCrudRepo extends CrudRepository<CacheEntity, String> {
    public CacheEntity findByAssignCode(String assignCode);
}
