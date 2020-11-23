package com.kakao.homework.repository.sprinkling;

import com.kakao.homework.data.sprinkling.entity.ReceiverInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/*
   repository
   MariaDb
   ReceiverInfo - Entity
 */
public interface ReceiverInfoRepository extends JpaRepository<ReceiverInfo, Long> {
}