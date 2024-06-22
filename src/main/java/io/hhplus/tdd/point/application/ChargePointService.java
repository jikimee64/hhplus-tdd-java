package io.hhplus.tdd.point.application;

import io.hhplus.tdd.point.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChargePointService {

    private final UserPointRepository userPointRepository;
    private final PointHistoryRepository pointHistoryRepository;

    public UserPoint charge(long userId, long amount) {
        UserPoint userPoint = userPointRepository.selectById(userId);

        ChargePointValidator chargePointValidator = new ChargePointValidator();
        if(!chargePointValidator.validateAmount(amount)){
            pointHistoryRepository.insert(
                    userId, amount, TransactionType.CHARGE_FAIL, System.currentTimeMillis()
            );
            return userPoint;
        }
        userPoint = userPointRepository.insertOrUpdate(userId, amount);
        pointHistoryRepository.insert(
                userId, amount, TransactionType.CHARGE_SUCCESS, System.currentTimeMillis()
        );
        return userPoint;
    }

}
