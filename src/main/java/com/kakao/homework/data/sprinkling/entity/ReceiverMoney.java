package com.kakao.homework.data.sprinkling.entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class ReceiverMoney {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ASSIGN_CODE")
    private DistMoney assignCode;
    private String recieverId;
    private int recieverMoney;
}
