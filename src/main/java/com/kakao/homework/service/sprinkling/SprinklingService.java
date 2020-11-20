package com.kakao.homework.service.sprinkling;

import com.kakao.homework.core.UUIDTokenMaker;
import com.kakao.homework.data.sprinkling.dto.SprinklingApiDto;
import com.kakao.homework.data.sprinkling.dto.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SprinklingService {
    //뿌릴금액, 뿌릴 인원을 요청값으로 받는다.
    UUIDTokenMaker uuidTokenMaker;

    public String Sprinkling(UserInfoDto header, SprinklingApiDto.Sprinkling body){
        //고유토큰 발급
        uuidTokenMaker.getNewToken();
        return uuidTokenMaker.getNewToken();
    }

    public String Receiver(UserInfoDto header, SprinklingApiDto.Receiver body){


        return "";
    }

    public String Search(UserInfoDto header, SprinklingApiDto.Search body){


        return "";
    }

}
