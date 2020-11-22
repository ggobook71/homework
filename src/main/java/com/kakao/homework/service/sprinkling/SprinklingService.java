package com.kakao.homework.service.sprinkling;

import com.kakao.homework.core.ResponseCreator;
import com.kakao.homework.core.exception.BusinessException;
import com.kakao.homework.core.exception.ErrorCode;
import com.kakao.homework.core.sprinkling.MoneyDistributor;
import com.kakao.homework.core.UUIDTokenMaker;
import com.kakao.homework.core.sprinkling.ReceiveMoney;
import com.kakao.homework.core.sprinkling.ReceiverInspector;
import com.kakao.homework.data.sprinkling.dto.SprinklingBodyDto;
import com.kakao.homework.data.sprinkling.dto.SprinklingHeaderDto;
import com.kakao.homework.data.sprinkling.entity.RedisEntity;
import com.kakao.homework.data.sprinkling.entity.DistMoney;
import com.kakao.homework.data.sprinkling.entity.ReceiverInfo;
import com.kakao.homework.repository.RedisCrudRepository;
import com.kakao.homework.repository.sprinkling.DistMoneyRepository;
import com.kakao.homework.repository.sprinkling.ReceiverMoneyRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class SprinklingService {
    private final UUIDTokenMaker uuidTokenMaker;
    private final MoneyDistributor moneyDistributor;
    private final RedisCrudRepository redisCrudRepository;
    private final RedissonClient redissonClient;
    private final DistMoneyRepository distMoneyRepository;
    private final ReceiverMoneyRepository receiverMoneyRepository;
    private final ResponseCreator responseCreator;
    private final ReceiverInspector receiverInspector;
    private final ReceiveMoney receiveMoney;
    private final long oneWeek = 7L;

    @Transactional
    public Map Sprinkling(SprinklingHeaderDto header, SprinklingBodyDto body) {
        //고유토큰 3자리 발급
        String token = uuidTokenMaker.getNewToken();
        //redis저장
        RedisEntity redisEntity = RedisEntity.builder().assignCode(token).userId(header.getUserId()).build();
        redisCrudRepository.save(redisEntity);
        //돈 랜덤 분배 및 저장
        List<ReceiverInfo> receiverMonies = moneyDistributor.RandomDist(body.getDistMoney(), body.getReceiveNum(), header, body, token);
        DistMoney distMoney = receiverMonies.get(0).getAssignCode();
        distMoneyRepository.save(distMoney);
        receiverMoneyRepository.saveAll(receiverMonies);
        return responseCreator.SingleKeyValueString("token", token);
    }

    @Transactional
    public Map Receiving(SprinklingHeaderDto header, String token) throws Exception {
        //distribute lock
        RReadWriteLock rwlock = redissonClient.getReadWriteLock(token);
        RLock lock = rwlock.readLock();
        lock.lock();
        boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS); //100초까지 대기 10초후에 락 해제
        if (res) {
            try {
                Optional<RedisEntity> cacheEntity = redisCrudRepository.findById(token);
                boolean enabled = cacheEntity.isPresent();
                if (enabled) {
                    DistMoney distMoney = distMoneyRepository.findByAssignCodeAndDistDateTimeBetween(
                            token, LocalDateTime.now().minusDays(oneWeek), LocalDateTime.now());
                    receiverInspector.ExceptionProcessor(cacheEntity, header, distMoney); //예외처리 로직
                    ReceiverInfo receiverInfo = receiveMoney.save(header, distMoney); //저장처리
                    receiverMoneyRepository.save(receiverInfo);
                    return responseCreator.SingleKeyValueInt("receive_money", receiverInfo.getRecieverMoney()); //성공시 해당 금액을 응답값으로 내려줍니다.
                } else {
                    throw new BusinessException("받기 가능시간이 만료 되었습니다.", ErrorCode.FAILED_GET_MONEY_MINUTE); //기간만료 처리 받기 실패
                }
            } finally {
                lock.unlock(); //처리 후 락 해제
            }
        }
        throw new BusinessException("유효 하지 않은 요청입니다.", ErrorCode.FAILED_GET_MONEY_BAD_REQUEST);
    }

    @Transactional
    public Map Searching(SprinklingHeaderDto header, String token) {

        DistMoney distMoney = distMoneyRepository.findByAssignCodeAndUserIdAndDistDateTimeBetween(
                token, header.getUserId(), LocalDateTime.now().minusDays(oneWeek), LocalDateTime.now()); //7일 기간 동안만 조회 가능
        if (distMoney == null) {
            throw new BusinessException("조회 권한이 없습니다.", ErrorCode.FAILED_SEARCH_RECEIVER_INFO);
        }
        return distMoney.getDistMoneyState();
    }
}
