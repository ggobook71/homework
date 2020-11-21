package com.kakao.homework.data.sprinkling.entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class DistMoney {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String assignCode;
    @OneToMany(mappedBy = "assignCode", cascade = CascadeType.ALL)
    private List<ReceiverMoney> receiverMoneyList = new ArrayList<>();
    private String userId;
    private String roomId;
    private int distMoney;
    private int receiveNum;
    private LocalDateTime distDateTime;
    @Builder
    public DistMoney(String assignCode, String userId, String roomId, int distMoney, int receiveNum, LocalDateTime distDateTime)
    {
        this.assignCode = assignCode;
        this.userId = userId;
        this.roomId = roomId;
        this.distMoney = distMoney;
        this.distDateTime = distDateTime;
        this.receiveNum = receiveNum;
    }
    /*public void setReceiverMoneyList(List<ReceiverMoney> receiverMoneyList)
    {
        this.receiverMoneyList = receiverMoneyList;
        if(this.receiverMoneyList != null && this.receiverMoneyList.size() > 0)
        {
            for (ReceiverMoney c : receiverMoneyList)
            {
                c.setAssignCode(this);
            }
        }
    }*/



}
