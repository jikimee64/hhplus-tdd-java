package io.hhplus.tdd.point.application;

import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.domain.UserPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPointService {

    private final UserPointRepository userPointRepository;

    public UserPoint point(long userId) {
        return userPointRepository.selectById(userId);
    }

}
