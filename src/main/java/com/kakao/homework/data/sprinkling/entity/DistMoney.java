package com.kakao.homework.data.sprinkling.entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hibernate.type.descriptor.java.JdbcDateTypeDescriptor.DATE_FORMAT;

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
    //@DateTimeFormat(pattern = DATE_TIME_FORMAT)
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

   public Map getDistMoneyState()
    {
        int receiveSumMoney =0;
        List<Map<String, Object>> receiver_Info = new ArrayList<>();

        for (ReceiverMoney receiverMoney:this.getReceiverMoneyList()) {
            if(receiverMoney.isEnableYn())
            {
                receiveSumMoney = receiveSumMoney + receiverMoney.getRecieverMoney();
                Map listmap = new HashMap();
                listmap.put("receive_money",receiverMoney.getRecieverMoney());
                listmap.put("receiver_id",receiverMoney.getRecieverId());
                receiver_Info.add(listmap);
            }
        }
        Map map = new HashMap();
        map.put("dist_date_time",this.distDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
        map.put("dist_money", this.distMoney);
        map.put("receive_sum_money", receiveSumMoney);
        map.put("receiver_Info", receiver_Info);
        return map;
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
