package com.kakao.homework.data.sprinkling.entity;

import com.sun.istack.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

/*
ReceiverInfo
받기 정보
*/
@NoArgsConstructor
@Getter
@Entity
public class ReceiverInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ASSIGN_CODE")
    @NonNull
    private DistMoney assignCode; //this is token
    private String recieverId; //받은사람 ID
    private int recieverMoney; //받을 돈
    private boolean enableYn; //받았는지 여부

    @Builder
    public ReceiverInfo(Long id, String recieverId, int recieverMoney, DistMoney assignCode, boolean enableYn) {
        this.id = id;
        this.recieverId = recieverId;
        this.recieverMoney = recieverMoney;
        this.assignCode = assignCode;
        this.enableYn = enableYn;
    }
}
