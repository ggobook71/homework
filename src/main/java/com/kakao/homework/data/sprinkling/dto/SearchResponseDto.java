package com.kakao.homework.data.sprinkling.dto;

import com.kakao.homework.data.sprinkling.entity.ReceiverMoney;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class SearchResponseDto {
    // 
    private String assignCode;
    @OneToMany(mappedBy = "assignCode", cascade = CascadeType.ALL)
    private List<ReceiverMoney> receiverMoneyList = new ArrayList<>();
    private String userId;
    private String roomId;
    private int distMoney;
    private int receiveNum;
    //@DateTimeFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime distDateTime;


}
