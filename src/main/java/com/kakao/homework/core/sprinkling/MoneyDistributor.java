package com.kakao.homework.core.sprinkling;

import com.kakao.homework.data.sprinkling.dto.SprinklingBodyDto;
import com.kakao.homework.data.sprinkling.dto.SprinklingHeaderDto;
import com.kakao.homework.data.sprinkling.entity.DistMoney;
import com.kakao.homework.data.sprinkling.entity.ReceiverInfo;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoneyDistributor {
    public List<ReceiverInfo> Distributor(SprinklingHeaderDto header, SprinklingBodyDto body, String token) {
        List<ReceiverInfo> receiverInfoList = new ArrayList<>();
        ReceiverInfo receiverInfo = new ReceiverInfo();
        Random random = new Random();
        int money = body.getDistMoney();
        int distnumber = body.getReceiveNum();
        DistMoney distMoney = DistMoney.builder()
                .assignCode(token)
                .distDateTime(LocalDateTime.now())
                .roomId(header.getRoomId())
                .userId(header.getUserId())
                .distMoney(body.getDistMoney())
                .receiveNum(body.getReceiveNum()).build();
        for (int i = 0; i < distnumber - 1; i++) {
            int div = random.nextInt(money - distnumber - i);
            money = money - div;
            receiverInfo = receiverInfo.builder().recieverMoney(div).assignCode(distMoney).enableYn(false).build();
            receiverInfoList.add(receiverInfo);
        }
        receiverInfo = receiverInfo.builder().recieverMoney(money).assignCode(distMoney).enableYn(false).build();
        receiverInfoList.add(receiverInfo);
        return receiverInfoList;
    }
}
