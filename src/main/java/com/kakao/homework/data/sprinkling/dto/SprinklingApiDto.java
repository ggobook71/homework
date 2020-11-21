package com.kakao.homework.data.sprinkling.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

public class SprinklingApiDto {
    @Getter
    @Setter
    public static class Sprinkling{
        @SerializedName(value = "Req_Sprinkling_Money")
        private int distMoney;
        @SerializedName(value = "Req_Sprinkling_Num")
        private int receiveNum;
    }
    @Getter
    @Setter
    public static class Receiver{

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
