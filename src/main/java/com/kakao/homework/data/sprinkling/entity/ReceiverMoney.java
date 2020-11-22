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
    private boolean enableYn;
    @Builder
    public ReceiverMoney(Long id, String recieverId, int recieverMoney, DistMoney assignCode, boolean enableYn)
    {
        this.id = id;
        this.recieverId = recieverId;
        this.recieverMoney = recieverMoney;
        this.assignCode = assignCode;
        this.enableYn = enableYn;
    }
}
