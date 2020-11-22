package com.kakao.homework.repository.sprinkling;

import com.kakao.homework.data.sprinkling.entity.DistMoney;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface DistMoneyRepository extends JpaRepository<DistMoney, Long> {
    DistMoney findByAssignCodeAndDistDateTimeBetween(String token, LocalDateTime dateTime, LocalDateTime now);

    DistMoney findByAssignCodeAndUserIdAndDistDateTimeBetween(String token, Integer userId, LocalDateTime dateTime, LocalDateTime now);
}