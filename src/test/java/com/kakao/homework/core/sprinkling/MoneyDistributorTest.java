package com.kakao.homework.core.sprinkling;

import com.kakao.homework.data.sprinkling.dto.SprinklingBodyDto;
import com.kakao.homework.data.sprinkling.dto.SprinklingHeaderDto;
import com.kakao.homework.data.sprinkling.entity.ReceiverInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MoneyDistributorTest {
    /*
    Distributor Test
    금액 분배 및 할당 기능 단위테스트
    */
    @Test
    public void Distributor() {
        SprinklingHeaderDto header = new SprinklingHeaderDto();
        SprinklingBodyDto body = new SprinklingBodyDto();
        String token = "a44";
        MoneyDistributor moneyDistributor = new MoneyDistributor();
        header.setUserId(123);
        header.setRoomId("555a");
        body.setDistMoney(9000);
        body.setReceiveNum(6);
        List<ReceiverInfo> receiverMonies = moneyDistributor.Distributor(header, body, token);
        assertTrue(receiverMonies.size() == 6);
    }

}