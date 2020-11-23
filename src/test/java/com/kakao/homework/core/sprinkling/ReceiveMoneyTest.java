package com.kakao.homework.core.sprinkling;

import com.kakao.homework.core.exception.BusinessException;
import com.kakao.homework.data.sprinkling.dto.SprinklingBodyDto;
import com.kakao.homework.data.sprinkling.dto.SprinklingHeaderDto;
import com.kakao.homework.data.sprinkling.entity.DistMoney;
import com.kakao.homework.data.sprinkling.entity.ReceiverInfo;
import com.kakao.homework.repository.sprinkling.DistMoneyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReceiveMoneyTest {
    /*
    Receive Money Test
    뿌릴 금액중 받을때 받은사용자정보를 저장할 데이터 선별 기능 단위 테스트
    */
    @Autowired
    DistMoneyRepository distMoneyRepository;

    @Test
    @Transactional
    void save() {
        String token = "499";
        Long oneWeek = 7L;

        ReceiveMoney receiveMoney = new ReceiveMoney();
        SprinklingHeaderDto header = new SprinklingHeaderDto();

        header.setRoomId("ABC");
        header.setUserId(112);

        DistMoney distMoney = distMoneyRepository.findByAssignCodeAndDistDateTimeBetween(
                token, LocalDateTime.now().minusDays(oneWeek), LocalDateTime.now());

        ReceiverInfo receiverInfo = receiveMoney.save(header, distMoney);
        assertTrue(token.equals(receiverInfo.getAssignCode().getAssignCode()));
    }

    /*
   Exception Receive Money Test
   모든사람이 받았는데 요청이 들어올 경우 예외 처리 기능 단위 테스트
   */
    @Test
    @Transactional
    void exceptionProcessor2() {
        String token = "499";
        Long oneWeek = 7L;

        ReceiveMoney receiveMoney = new ReceiveMoney();
        SprinklingHeaderDto header = new SprinklingHeaderDto();

        header.setRoomId("ABC");
        header.setUserId(112);

        DistMoney distMoney = distMoneyRepository.findByAssignCodeAndDistDateTimeBetween(
                token, LocalDateTime.now().minusDays(oneWeek), LocalDateTime.now());

        BusinessException thrown = assertThrows(
                BusinessException.class,
                () -> receiveMoney.save(header, distMoney),
                "Expected doThing() to throw, but it didn't"
        );
        assertTrue(thrown.getErrorCode().getMessage().contains("할당 된 금액을 받기 실패 하였습니다."));
    }

}