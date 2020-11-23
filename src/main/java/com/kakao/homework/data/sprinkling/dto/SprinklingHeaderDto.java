package com.kakao.homework.data.sprinkling.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
SprinklingHeaderDto
Request header 정보
*/
@Getter
@Setter
@ToString
public class SprinklingHeaderDto {
    private Integer userId; // X-USER-ID
    private String roomId; // X-ROOM-ID
}
