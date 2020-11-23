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
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class SprinklingService {
    private final long oneWeek = 7L;
    private final RedisCrudRepository redisCrudRepository;
    private final DistMoneyRepository distMoneyRepository;
    private final ReceiverMoneyRepository receiverMoneyRepository;
    private final RedissonClient redissonClient;
    private final ResponseCreator responseCreator;
    private final ReceiverInspector receiverInspector;

    @Autowired
    public SprinklingService(RedisCrudRepository redisCrudRepository
            , DistMoneyRepository distMoneyRepository
            , ReceiverMoneyRepository receiverMoneyRepository
            , RedissonClient redissonClient
            , ResponseCreator responseCreator
            , ReceiverInspector receiverInspector) {
        this.redisCrudRepository = redisCrudRepository;
        this.distMoneyRepository = distMoneyRepository;
        this.receiverMoneyRepository = receiverMoneyRepository;
        this.redissonClient = redissonClient;
        this.responseCreator = responseCreator;
        this.receiverInspector = receiverInspector;
    }

    @Transactional
    public Map<String, String> Sprinkling(SprinklingHeaderDto header, SprinklingBodyDto body) {
        receiverInspector.exceptionProcessor(header, body); //예외처리 로직
        UUIDTokenMaker uuidTokenMaker = new UUIDTokenMaker();
        MoneyDistributor moneyDistributor = new MoneyDistributor();
        String token = uuidTokenMaker.getNewToken(); //고유토큰 3자리 발급
        RedisEntity redisEntity = RedisEntity.builder().assignCode(token).userId(header.getUserId()).build(); //redis저장
        redisCrudRepository.save(redisEntity);
        List<ReceiverInfo> receiverMonies = moneyDistributor.Distributor(header, body, token);
        DistMoney distMoney = receiverMonies.get(0).getAssignCode();
        distMoneyRepository.save(distMoney); //돈 랜덤 분배 및 저장
        receiverMoneyRepository.saveAll(receiverMonies);
        return responseCreator.SingleKeyValueString("token", token);
    }

    @Transactional
    public Map<String, Integer> Receiving(SprinklingHeaderDto header, String token) throws Exception {
        ReceiveMoney receiveMoney = new ReceiveMoney();
        RReadWriteLock rwlock = redissonClient.getReadWriteLock(token); //distribute lock
        RLock lock = rwlock.readLock();
        lock.lock();
        boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS); //충돌시 100초까지 대기 10초후에 락 해제
        if (res) {
            try {
                Optional<RedisEntity> cacheEntity = redisCrudRepository.findById(token);
                boolean enabled = cacheEntity.isPresent();
                receiverInspector.exceptionProcessor(enabled); //예외처리 로직
                if (enabled) {
                    DistMoney distMoney = distMoneyRepository.findByAssignCodeAndDistDateTimeBetween(
                            token, LocalDateTime.now().minusDays(oneWeek), LocalDateTime.now());
                    receiverInspector.exceptionProcessor(cacheEntity, header, distMoney); //예외처리 로직
                    ReceiverInfo receiverInfo = receiveMoney.save(header, distMoney); //저장처리
                    receiverMoneyRepository.save(receiverInfo);
                    return responseCreator.SingleKeyValueInt("receive_money", receiverInfo.getRecieverMoney()); //성공시 해당 금액을 응답값으로 내려줍니다.
                }
            } finally {
                lock.unlock(); //처리 후 락 해제
            }
        }
        throw new BusinessException("유효 하지 않은 요청입니다.", ErrorCode.FAILED_GET_MONEY_BAD_REQUEST);
    }

    @Transactional
    public Map<String, Object> Searching(SprinklingHeaderDto header, String token) {
        DistMoney distMoney = distMoneyRepository.findByAssignCodeAndUserIdAndDistDateTimeBetween(
                token, header.getUserId(), LocalDateTime.now().minusDays(oneWeek), LocalDateTime.now()); //7일 기간 동안만 조회 가능
        receiverInspector.exceptionProcessor(distMoney); //예외처리 로직
        return distMoney.getDistMoneyState();
    }
}
