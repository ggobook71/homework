package com.kakao.homework.data.sprinkling.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
DistMoney
뿌리기 정보
*/
@NoArgsConstructor
@Getter
@Entity
public class DistMoney {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String assignCode; // this is token
    @OneToMany(mappedBy = "assignCode", cascade = CascadeType.ALL) //관계매핑(TOKEN)
    private List<ReceiverInfo> receiverInfoList = new ArrayList<>();
    private Integer userId; //뿌린사람ID
    private String roomId; //방ID
    private int distMoney; //금액
    private int receiveNum; //받을사람수
    //@DateTimeFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime distDateTime; //뿌리기 시작한 시간

    @Builder
    public DistMoney(String assignCode, Integer userId, String roomId, int distMoney, int receiveNum, LocalDateTime distDateTime) {
        this.assignCode = assignCode;
        this.userId = userId;
        this.roomId = roomId;
        this.distMoney = distMoney;
        this.distDateTime = distDateTime;
        this.receiveNum = receiveNum;
    }

    public Map getDistMoneyState() {
        int receiveSumMoney = 0;
        List<Map<String, Object>> receiver_Info = new ArrayList<>();

        for (ReceiverInfo receiverInfo : this.getReceiverInfoList()) {
            if (receiverInfo.isEnableYn()) {
                receiveSumMoney = receiveSumMoney + receiverInfo.getRecieverMoney();
                Map listmap = new HashMap();
                listmap.put("receive_money", receiverInfo.getRecieverMoney());
                listmap.put("receiver_id", receiverInfo.getRecieverId());
                receiver_Info.add(listmap);
            }
        }
        Map map = new HashMap();
        map.put("dist_date_time", this.distDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
        map.put("dist_money", this.distMoney);
        map.put("receive_sum_money", receiveSumMoney);
        map.put("receiver_Info", receiver_Info);
        return map;
    }
}
