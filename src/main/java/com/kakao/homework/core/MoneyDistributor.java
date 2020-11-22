package com.kakao.homework.core;

import com.kakao.homework.data.sprinkling.dto.SprinklingApiDto;
import com.kakao.homework.data.sprinkling.dto.UserInfoDto;
import com.kakao.homework.data.sprinkling.entity.DistMoney;
import com.kakao.homework.data.sprinkling.entity.ReceiverMoney;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.transform.Result;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class MoneyDistributor {
    public List<ReceiverMoney> RandomDist(int money, int distnumber, UserInfoDto header, SprinklingApiDto.Sprinkling body, String token) throws Exception
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
