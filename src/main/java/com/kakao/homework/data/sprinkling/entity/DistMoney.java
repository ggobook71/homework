package com.kakao.homework.data.sprinkling.entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private List<ReceiverMoney> receiverMoneyList;
    private String userId;
    private String roomId;
    private int distMoney;
    private int receiveNum;
    private LocalDateTime distDateTime;
}
