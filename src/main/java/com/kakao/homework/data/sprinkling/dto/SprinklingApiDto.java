package com.kakao.homework.data.sprinkling.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

public class SprinklingApiDto {
    @Getter
    @Setter
    public static class Sprinkling{
        private int distMoney;
        private int receiveNum;
    }
    @Getter
    @Setter
    public static class Receiver{
        private String token;
    }

    @Getter
    @Setter
    public static class Search{

    }
    @Getter
    @Setter
    public static class Redis0{

    }


}
