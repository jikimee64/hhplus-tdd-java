package io.hhplus.tdd.point.application;

import io.hhplus.tdd.point.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {

    private final UserPointRepository userPointRepository;
    private final PointHistoryRepository pointHistoryRepository;

    public UserPoint charge(ChargePointCommand command) {
        UserPoint userPoint = userPointRepository.insertOrUpdate(command.userId(), command.amount());
        pointHistoryRepository.insert(
                command.userId(), command.amount(), TransactionType.CHARGE_SUCCESS, System.currentTimeMillis()
        );
        return userPoint;
    }

    public List<PointHistory> histories(long userId) {
        return pointHistoryRepository.selectAllByUserId(userId);
    }

    public PointHistory history(long userId) {
        return pointHistoryRepository.selectOneByUserId(userId);
    }
}
