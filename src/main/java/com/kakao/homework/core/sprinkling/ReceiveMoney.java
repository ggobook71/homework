package com.kakao.homework.core.sprinkling;

import com.kakao.homework.core.exception.BusinessException;
import com.kakao.homework.core.exception.ErrorCode;
import com.kakao.homework.data.sprinkling.dto.SprinklingHeaderDto;
import com.kakao.homework.data.sprinkling.entity.DistMoney;
import com.kakao.homework.data.sprinkling.entity.ReceiverInfo;
import org.springframework.stereotype.Component;

@Component
public class ReceiveMoney {
    public ReceiverInfo save(SprinklingHeaderDto header, DistMoney distMoney) throws Exception {
        for (ReceiverInfo receiverInfo : distMoney.getReceiverInfoList()) {
            if (!receiverInfo.isEnableYn()) {
                receiverInfo = receiverInfo.builder()
                        .id(receiverInfo.getId())
                        .recieverMoney(receiverInfo.getRecieverMoney())
                        .assignCode(distMoney)
                        .recieverId(header.getUserId().toString())
                        .enableYn(true).build();
                return receiverInfo;
            }
        }
        throw new BusinessException("할당 된 금액을 받기 실패 하였습니다.", ErrorCode.FAILED_GET_MONEY);
    }
}
