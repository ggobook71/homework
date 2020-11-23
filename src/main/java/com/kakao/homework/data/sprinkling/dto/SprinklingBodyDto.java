package com.kakao.homework.data.sprinkling.dto;

import lombok.Getter;
import lombok.Setter;

/*
SprinklingBodyDto
Request Body 정보
*/
@Getter
@Setter
public class SprinklingBodyDto {
    private int distMoney; //뿌릴금액
    private int receiveNum; //받을 사용자 수
}