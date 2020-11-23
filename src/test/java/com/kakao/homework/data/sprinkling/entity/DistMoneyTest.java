package com.kakao.homework.data.sprinkling.entity;

import com.kakao.homework.repository.sprinkling.DistMoneyRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/*
 Repository Stored Searching
 데이터 조회 관련 단위 테스트(mariaDB) 및 데이터 양방향 매핑관련 단위테스트(manytoone생략)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class DistMoneyTest {
    @Autowired
    private DistMoneyRepository distMoneyRepository;

    @Test
    void getDistMoneyState() {
        DistMoney distMoney = distMoneyRepository.findByAssignCodeAndUserIdAndDistDateTimeBetween(
                "a75", 123, LocalDateTime.now().minusDays(7), LocalDateTime.now());
        Map map = distMoney.getDistMoneyState();
        assertFalse(map.isEmpty());
    }

    @Test
    void getId() {
        DistMoney distMoney = distMoneyRepository.findByAssignCodeAndUserIdAndDistDateTimeBetween(
                "a75", 123, LocalDateTime.now().minusDays(7), LocalDateTime.now());
        assertFalse(distMoney.getId() == null || distMoney.getId() == 0);
    }

    @Test
    void getAssignCode() {
        DistMoney distMoney = distMoneyRepository.findByAssignCodeAndUserIdAndDistDateTimeBetween(
                "a75", 123, LocalDateTime.now().minusDays(7), LocalDateTime.now());
        assertTrue(distMoney.getAssignCode().length() > 0);
    }

    @Test
    void 연관관계매핑() {
        DistMoney distMoney = distMoneyRepository.findByAssignCodeAndUserIdAndDistDateTimeBetween(
                "a75", 123, LocalDateTime.now().minusDays(7), LocalDateTime.now());
        assertTrue(distMoney.getReceiverInfoList().size() > 0);
        assertTrue(distMoney.getReceiverInfoList().get(0).getRecieverId().length() > 0);
    }

    @Test
    void getUserId() {
        DistMoney distMoney = distMoneyRepository.findByAssignCodeAndUserIdAndDistDateTimeBetween(
                "a75", 123, LocalDateTime.now().minusDays(7), LocalDateTime.now());
        assertTrue(distMoney.getUserId() > 0);
    }

    @Test
    void getRoomId() {
        DistMoney distMoney = distMoneyRepository.findByAssignCodeAndUserIdAndDistDateTimeBetween(
                "a75", 123, LocalDateTime.now().minusDays(7), LocalDateTime.now());
        assertTrue(distMoney.getRoomId().length() > 0);
    }

    @Test
    void getDistMoney() {
        DistMoney distMoney = distMoneyRepository.findByAssignCodeAndUserIdAndDistDateTimeBetween(
                "a75", 123, LocalDateTime.now().minusDays(7), LocalDateTime.now());
        assertTrue(distMoney.getDistMoney() > 0);
    }

    @Test
    void getReceiveNum() {
        DistMoney distMoney = distMoneyRepository.findByAssignCodeAndUserIdAndDistDateTimeBetween(
                "a75", 123, LocalDateTime.now().minusDays(7), LocalDateTime.now());
        assertTrue(distMoney.getReceiveNum() > 0);
    }

    @Test
    void getDistDateTime() {
        DistMoney distMoney = distMoneyRepository.findByAssignCodeAndUserIdAndDistDateTimeBetween(
                "a75", 123, LocalDateTime.now().minusDays(7), LocalDateTime.now());
        assertTrue(distMoney.getDistDateTime().toString().length() > 0);
    }
}