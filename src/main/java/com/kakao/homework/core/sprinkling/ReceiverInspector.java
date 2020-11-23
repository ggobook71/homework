package com.kakao.homework.core.sprinkling;

import com.kakao.homework.core.exception.BusinessException;
import com.kakao.homework.core.exception.ErrorCode;
import com.kakao.homework.data.sprinkling.dto.SprinklingBodyDto;
import com.kakao.homework.data.sprinkling.dto.SprinklingHeaderDto;
import com.kakao.homework.data.sprinkling.entity.RedisEntity;
import com.kakao.homework.data.sprinkling.entity.DistMoney;
import com.kakao.homework.data.sprinkling.entity.ReceiverInfo;
import org.springframework.stereotype.Component;

import java.util.Optional;

/*
ReceiverInspector
예외처리 제약사항
*/
@Component
public class ReceiverInspector {
    public void exceptionProcessor(Optional<RedisEntity> cacheEntity, SprinklingHeaderDto header, DistMoney distMoney) throws BusinessException {
        for (ReceiverInfo receiverInfo : distMoney.getReceiverInfoList()) {
            if (distMoney.getUserId().toString().equals(header.getUserId().toString())){ // 뿌린자가 받기 요청시
                throw new BusinessException("받기 대상자가 아닙니다.", ErrorCode.FAILED_GET_MONEY_UN_TARGET);
            }
            if (receiverInfo.getRecieverId() != null) {
                if (receiverInfo.getRecieverId().equals(header.getUserId().toString())){ // 받았던 사람이 받기 요청시
                    throw new BusinessException("받기는 한번만 가능합니다.", ErrorCode.FAILED_GET_MONEY_ONE_MORE);
                }
            }
        }
    }
    public void exceptionProcessor(SprinklingHeaderDto header, SprinklingBodyDto body) throws BusinessException {
        //null이 아닌 값 0 체크
        if (body.getDistMoney() == 0 || body.getReceiveNum() == 0) {
            throw new BusinessException("금액이나 뿌릴인원이 존재하지 않습니다.", ErrorCode.FAILED_SAVE_MONEY);
        }
    }
    public void exceptionProcessor(DistMoney distMoney) throws BusinessException {
        if (distMoney == null) {
            throw new BusinessException("조회 권한이 없거나 기간이 만료 되었습니다.", ErrorCode.FAILED_SEARCH_RECEIVER_INFO);
        }
    }
    public void exceptionProcessor(boolean enabled) throws BusinessException {
        if (!enabled) {
            throw new BusinessException("받기 가능시간이 만료 되었습니다.", ErrorCode.FAILED_GET_MONEY_MINUTE); //10분 기간만료 받기 실패
        }
    }


}
