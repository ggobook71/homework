package com.kakao.homework.core;

import com.kakao.homework.data.sprinkling.dto.SprinklingBodyDto;
import com.kakao.homework.data.sprinkling.dto.SprinklingHeaderDto;
import com.kakao.homework.data.sprinkling.entity.DistMoney;
import com.kakao.homework.data.sprinkling.entity.ReceiverMoney;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class MoneyDistributor {
    public List<ReceiverMoney> RandomDist(int money, int distnumber, SprinklingHeaderDto header, SprinklingBodyDto body, String token) throws Exception
    {
        List<ReceiverMoney> receiverMoneyList = new ArrayList<>();
        ReceiverMoney receiverMoney = new ReceiverMoney();
        Random random = new Random();
        DistMoney distMoney = DistMoney.builder()
                .assignCode(token)
                .distDateTime(LocalDateTime.now())
                .roomId(header.getRoomId())
                .userId(header.getUserId().toString())
                .distMoney(body.getDistMoney())
                .receiveNum(body.getReceiveNum()).build();
        for(int i=0; i<distnumber-1; i++)
        {
            int div = random.nextInt(money-distnumber-i);
            money = money - div;
            receiverMoney = receiverMoney.builder().recieverMoney(div).assignCode(distMoney).enableYn(false).build();
            receiverMoneyList.add(receiverMoney);
        }
        receiverMoney = receiverMoney.builder().recieverMoney(money).assignCode(distMoney).enableYn(false).build();
        receiverMoneyList.add(receiverMoney);
        return receiverMoneyList;
    }
}
