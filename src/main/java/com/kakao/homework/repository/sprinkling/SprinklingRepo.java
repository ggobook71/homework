package com.kakao.homework.repository.sprinkling;
import com.kakao.homework.data.sprinkling.entity.DistMoney;
import com.kakao.homework.data.sprinkling.entity.ReceiverMoney;
import org.springframework.data.jpa.repository.JpaRepository;

public class SprinklingRepo {
    public interface DistMoneyRepository extends JpaRepository<DistMoney, Long>{}
    public interface ReceiverMoneyRepository extends JpaRepository<ReceiverMoney, Long>{}
}
