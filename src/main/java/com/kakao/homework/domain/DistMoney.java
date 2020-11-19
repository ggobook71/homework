package com.kakao.homework.domain;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class DistMoney {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //고유토큰
    private String assignCode;
    //고유토큰매핑
    @OneToMany(mappedBy = "assignCode", cascade = CascadeType.ALL)
    private List<ReceiverMoney> receiverMoneyList = new ArrayList<ReceiverMoney>();
    //x-user-id
    private String userId;
    //x-room-id
    private String roomId;
    //뿌릴금액
    private int distMoney;
    //뿌릴인원
    private int receiveNum;
    //뿌린시각
    private LocalDateTime distDateTime;
}
