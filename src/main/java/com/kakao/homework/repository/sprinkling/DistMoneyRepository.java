package com.kakao.homework.repository.sprinkling;

import com.kakao.homework.data.sprinkling.entity.DistMoney;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

/*
   repository
   MariaDb
   DstMoney - Entity
 */
public interface DistMoneyRepository extends JpaRepository<DistMoney, Long> {
    DistMoney findByAssignCodeAndDistDateTimeBetween(String token, LocalDateTime dateTime, LocalDateTime now); //받는사용자가 조회할때
    DistMoney findByAssignCodeAndUserIdAndDistDateTimeBetween(String token, Integer userId, LocalDateTime dateTime, LocalDateTime now); //뿌린사용자가 조회할때
}