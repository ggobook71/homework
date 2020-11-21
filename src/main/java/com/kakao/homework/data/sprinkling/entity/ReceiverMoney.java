package com.kakao.homework.data.sprinkling.entity;
import com.sun.istack.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @Nullable
    private String recieverId;
    private int recieverMoney;
    @Builder
    public ReceiverMoney(String recieverId, int recieverMoney, DistMoney assignCode)
    {
        this.recieverId = recieverId;
        this.recieverMoney = recieverMoney;
        this.assignCode = assignCode;
    }
}
