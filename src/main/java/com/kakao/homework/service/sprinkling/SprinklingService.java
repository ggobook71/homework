package com.kakao.homework.service.sprinkling;

import com.kakao.homework.core.MoneyDistributor;
import com.kakao.homework.core.UUIDTokenMaker;
import com.kakao.homework.data.sprinkling.dto.SprinklingApiDto;
import com.kakao.homework.data.sprinkling.dto.UserInfoDto;
import com.kakao.homework.data.sprinkling.entity.CacheEntity;
import com.kakao.homework.data.sprinkling.entity.DistMoney;
import com.kakao.homework.data.sprinkling.entity.ReceiverMoney;
import com.kakao.homework.repository.RedisCrudRepo;
import com.kakao.homework.repository.sprinkling.DistMoneyRepository;
import com.kakao.homework.repository.sprinkling.ReceiverMoneyRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SprinklingService {
    //뿌릴금액, 뿌릴 인원을 요청값으로 받는다.
    private final UUIDTokenMaker uuidTokenMaker;
    private final MoneyDistributor moneyDistributor;
    private final RedisCrudRepo redisCrudRepo;
    private final RedissonClient redissonClient;
    private final DistMoneyRepository distMoneyRepository;
    private final ReceiverMoneyRepository receiverMoneyRepository;

    @Transactional
    public String Sprinkling(UserInfoDto header, SprinklingApiDto.Sprinkling body) throws Exception {
        String token;
        String userId;
        //CacheEntity cacheEntity;
        //고유토큰 3자리 발급
        token=uuidTokenMaker.getNewToken();
        userId=header.getUserId().toString();
        CacheEntity cacheEntity = CacheEntity.builder().assignCode(token).userId(userId).build();
        //redis저장
        redisCrudRepo.save(cacheEntity);
        //db저장
        List<ReceiverMoney> receiverMonies;
        receiverMonies = moneyDistributor.RandomDist(body.getDistMoney(), body.getReceiveNum(),header,body,token);
        DistMoney distMoney = receiverMonies.get(0).getAssignCode();
        distMoneyRepository.save(distMoney);
        receiverMoneyRepository.saveAll(receiverMonies);
        return token;
    }

    public String Receiver(UserInfoDto header, SprinklingApiDto.Receiver body){


        return "";
    }

    public String Search(UserInfoDto header, SprinklingApiDto.Search body){


        return "";
    }

}
