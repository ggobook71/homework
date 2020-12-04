package com.kakao.homework.core.sprinkling;

import com.kakao.homework.core.exception.BusinessException;
import com.kakao.homework.data.sprinkling.dto.SprinklingBodyDto;
import com.kakao.homework.data.sprinkling.dto.SprinklingHeaderDto;
import com.kakao.homework.data.sprinkling.entity.DistMoney;
import com.kakao.homework.data.sprinkling.entity.RedisEntity;
import com.kakao.homework.repository.RedisCrudRepository;
import com.kakao.homework.repository.sprinkling.DistMoneyRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReceiverInspectorTest {
    /*
    Exception Processor Test
    예외처리 기능 단위 테스트
    */
    @Autowired
    RedisCrudRepository redisCrudRepository;
    @Autowired
    DistMoneyRepository distMoneyRepository;
    @Autowired
    ReceiverInspector receiverInspector;

    /*
    Exception Processor Test
    뿌린돈 받는부분 예외처리 기능 단위 테스트(redis 10분 ttl 만료시 자동 logic 사용 권 박탈)
    */
    @Test
    @Transactional
    void exceptionProcessor() {
        String token = "499";
        Long oneWeek = 7L;
        Optional<RedisEntity> cacheEntity = redisCrudRepository.findById(token);
        if (cacheEntity.isPresent()) {
            DistMoney distMoney = distMoneyRepository.findByAssignCodeAndDistDateTimeBetween(token, LocalDateTime.now().minusDays(oneWeek), LocalDateTime.now());
            SprinklingHeaderDto sprinklingHeaderDto = new SprinklingHeaderDto();
            sprinklingHeaderDto.setUserId(145);

            BusinessException thrown = assertThrows(
                    BusinessException.class,
                    () -> receiverInspector.exceptionProcessor(cacheEntity, sprinklingHeaderDto, distMoney),
                    "Expected doThing() to throw, but it didn't"
            );
            assertTrue(thrown.getErrorCode().getMessage().contains("받기"));

        }
    }

    /*
    Exception Processor Test
    뿌릴때 금액 또는 사람수 이상시 예외처리 기능 단위 테스트
    */
    @Test
    @Transactional
    void exceptionProcessor2() {

        SprinklingHeaderDto sprinklingHeaderDto = new SprinklingHeaderDto();
        sprinklingHeaderDto.setUserId(145);
        SprinklingBodyDto sprinklingBodyDto = new SprinklingBodyDto();
        sprinklingBodyDto.setReceiveNum(0);
        sprinklingBodyDto.setDistMoney(0);
        BusinessException thrown = assertThrows(
                BusinessException.class,
                () -> receiverInspector.exceptionProcessor(sprinklingHeaderDto, sprinklingBodyDto),
                "Expected doThing() to throw, but it didn't"
        );
        assertTrue(thrown.getErrorCode().getMessage().contains("금액이나 뿌릴인원이 존재하지 않습니다."));
    }
}

