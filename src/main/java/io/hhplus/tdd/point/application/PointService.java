package io.hhplus.tdd.point.application;

import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.domain.UserPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {

    private final UserPointRepository userPointRepository;

    public UserPoint charge(long pointId, long amount) {
        return userPointRepository.insertOrUpdate(pointId, amount);
    }
}
