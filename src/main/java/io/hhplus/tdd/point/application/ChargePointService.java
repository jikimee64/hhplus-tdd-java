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
    private final PointServiceLock pointServiceLock;

    public UserPoint charge(long userId, long amount) {
        pointServiceLock.getLock();
        try {
            UserPoint userPoint = userPointRepository.selectById(userId);

            ChargePointValidator chargePointValidator = new ChargePointValidator();
            if (!chargePointValidator.validateAmount(amount)) {
                pointHistoryRepository.insert(
                        userId, amount, TransactionType.CHARGE_FAIL, System.currentTimeMillis()
                );
                return userPoint;
            }

            userPoint = userPointRepository.insertOrUpdate(userId, userPoint.point() + amount);
            pointHistoryRepository.insert(
                    userId, amount, TransactionType.CHARGE_SUCCESS, System.currentTimeMillis()
            );
            return userPoint;
        } finally {
            pointServiceLock.releaseLock();
        }
    }

}
