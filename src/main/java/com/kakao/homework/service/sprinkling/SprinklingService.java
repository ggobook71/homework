package com.kakao.homework.service.sprinkling;

import com.kakao.homework.core.MoneyDistributor;
import com.kakao.homework.core.UUIDTokenMaker;
import com.kakao.homework.data.sprinkling.dto.SprinklingBodyDto;
import com.kakao.homework.data.sprinkling.dto.SprinklingHeaderDto;
import com.kakao.homework.data.sprinkling.entity.CacheEntity;
import com.kakao.homework.data.sprinkling.entity.DistMoney;
import com.kakao.homework.data.sprinkling.entity.ReceiverMoney;
import com.kakao.homework.repository.RedisCrudRepo;
import com.kakao.homework.repository.sprinkling.DistMoneyRepository;
import com.kakao.homework.repository.sprinkling.ReceiverMoneyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Slf4j
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
    public String Sprinkling(SprinklingHeaderDto header, SprinklingBodyDto body) throws Exception {
        String token;
        String userId;
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

    @Transactional
    public String Receiver(SprinklingHeaderDto header, String token) throws Exception{

        //락구현 해야함...



        Optional<CacheEntity> cacheEntity = redisCrudRepo.findById(token);
        boolean enabled = cacheEntity.isPresent();
        if(enabled)
        {
            //Optional<DistMoney> distMoney = distMoneyRepository.findByAssignCodeAndDistDateTime(token,LocalDateTime.now().minusDays(7)); //7일 기간 동안만 조회
            DistMoney distMoney = distMoneyRepository.findByAssignCodeAndDistDateTimeBetween(token, LocalDateTime.now().minusDays(7L), LocalDateTime.now());
            //DistMoney distMoney = distMoneyRepository.findByAssignCode(token);
            //List<ReceiverMoney> receiverMonies = receiverMoneyRepository.findByAssignCodeAndEnableYn(token, false);
            if(cacheEntity.get().getUserId().equals(header.getUserId().toString())) //뿌린자가 받기 요청시 redis
            {
                return "받기 대상자가 아닙니다. A1 : " + LocalDateTime.now(); //Exception 나중에 처리 해야함.
            }
            /*if(!distMoney.isPresent()) //7일이 지난데이터를 받기 요청시
            {
                return "유효하지 않은 요청입니다."; //Exception 나중에 처리 해야함.
            }*/

            for (ReceiverMoney receiverMoney : distMoney.getReceiverMoneyList()) {
                if(receiverMoney.getRecieverId()!=null) {
                    if (receiverMoney.getRecieverId().equals(header.getUserId().toString())) // 받았던 사람이 받기 요청시
                    {
                        return "유효하지 않은 요청입니다. A2 : " + LocalDateTime.now();
                    }
                }
            }

            for (ReceiverMoney receiverMoney : distMoney.getReceiverMoneyList()) {
                if(!receiverMoney.isEnableYn()){
                    if(receiverMoney.getAssignCode().getUserId().equals(header.getUserId().toString())) // 뿌린자가 받기 요청시 mariadb
                    {
                        return "유효하지 않은 요청입니다. A3 : " + LocalDateTime.now();
                    }
                    receiverMoney = receiverMoney.builder()
                            .id(receiverMoney.getId())
                            .recieverMoney(receiverMoney.getRecieverMoney())
                            .assignCode(distMoney)
                            .recieverId(header.getUserId().toString())
                            .enableYn(true).build();
                    receiverMoneyRepository.save(receiverMoney);
                    return "sucess : " + receiverMoney.getRecieverMoney(); //성공시 해당 금액을 응답값으로 내려줍니다.
                }
            }
            return "token : " + cacheEntity.get().getAssignCode(); //기간이 만료 되었거나 모든 사용자가 돈을 받았습니다.
        }
        else //기간만료 처리 받기 실패
        {
            return "기간이 만료 되었습니다. A4 : " + LocalDateTime.now(); //Exception 나중에 처리 해야함.
        }
    }

    @Transactional
    public Map Search(SprinklingHeaderDto header, String token){
        DistMoney distMoney = distMoneyRepository.findByAssignCodeAndUserIdAndDistDateTimeBetween(
                token, header.getUserId().toString(), LocalDateTime.now().minusDays(7L), LocalDateTime.now()); //7일 기간 동안만 조회 가능
        return distMoney.getDistMoneyState();
    }

}
