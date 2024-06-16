package io.hhplus.tdd.point.application;

import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.domain.UserPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsePointService {

    private final UserPointRepository userPointRepository;

    public boolean usePoint(long userId, long amount) {
        UserPoint userPoint = userPointRepository.selectById(userId);
        UserPoint usedUserPoint = userPoint.usePoint(amount);
        userPointRepository.insertOrUpdate(usedUserPoint.userId(), usedUserPoint.point());
        return true;
    }

}
