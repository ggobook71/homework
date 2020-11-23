package com.kakao.homework.repository.sprinkling;

import com.kakao.homework.data.sprinkling.entity.ReceiverInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiverMoneyRepository extends JpaRepository<ReceiverInfo, Long> {}