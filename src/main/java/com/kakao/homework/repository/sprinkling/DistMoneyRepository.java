package com.kakao.homework.repository.sprinkling;
import com.kakao.homework.data.sprinkling.entity.DistMoney;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DistMoneyRepository extends JpaRepository<DistMoney, Long> {
    DistMoney findByAssignCode(String assignCode);
    //Optional<DistMoney> findByIdAndDistDateTimeAfter(String token, LocalDateTime dateTime);
    DistMoney findByAssignCodeAndDistDateTimeBetween(String token, LocalDateTime dateTime, LocalDateTime now);
}