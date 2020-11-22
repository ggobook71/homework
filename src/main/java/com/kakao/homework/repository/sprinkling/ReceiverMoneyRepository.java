package com.kakao.homework.repository.sprinkling;
import com.kakao.homework.data.sprinkling.entity.ReceiverMoney;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReceiverMoneyRepository extends JpaRepository<ReceiverMoney, Long> {
    List<ReceiverMoney> findByAssignCodeAndEnableYn(String assignCode, boolean enableYn);
    //ReceiverMoney findByAssignCodeAndRecieverId(String assignCode, String receiverId);
}