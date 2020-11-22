package com.kakao.homework.core.sprinkling;

import com.kakao.homework.core.exception.BusinessException;
import com.kakao.homework.core.exception.ErrorCode;
import com.kakao.homework.data.sprinkling.dto.SprinklingHeaderDto;
import com.kakao.homework.data.sprinkling.entity.RedisEntity;
import com.kakao.homework.data.sprinkling.entity.DistMoney;
import com.kakao.homework.data.sprinkling.entity.ReceiverInfo;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReceiverInspector {
    public void ExceptionProcessor(Optional<RedisEntity> cacheEntity, SprinklingHeaderDto header, DistMoney distMoney) throws Exception {
        if (cacheEntity.get().getUserId().equals(header.getUserId().toString())) //뿌린자가 받기 요청시 redis
        {
            throw new BusinessException("받기 대상자가 아닙니다.", ErrorCode.FAILED_GET_MONEY_UN_TARGET);//"받기 대상자가 아닙니다.
        }

        for (ReceiverInfo receiverInfo : distMoney.getReceiverInfoList()) {
            if (receiverInfo.getRecieverId() != null) {
                if (receiverInfo.getRecieverId().equals(header.getUserId().toString())) // 받았던 사람이 받기 요청시
                {
                    throw new BusinessException("받기는 한번만 가능합니다.", ErrorCode.FAILED_GET_MONEY_ONE_MORE);
                }
            }
        }

        for (ReceiverInfo receiverInfo : distMoney.getReceiverInfoList()) {
            if (!receiverInfo.isEnableYn()) {
                if (receiverInfo.getAssignCode().getUserId().equals(header.getUserId().toString())) // 뿌린자가 받기 요청시 mariadb
                {
                    throw new BusinessException("받기 대상자가 아닙니다.", ErrorCode.FAILED_GET_MONEY_UN_TARGET);
                }
            }
        }
    }
}
