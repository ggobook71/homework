package com.kakao.homework.domain;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class ReceiverMoney {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //고유토큰매핑
    @ManyToOne
    @JoinColumn(name = "ASSIGN_CODE")
    private DistMoney assignCode;
    //받은사용자아이디
    private String recieverId;
    //받은금액
    private int recieverMoney;

}
