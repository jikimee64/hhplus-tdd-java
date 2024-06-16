package io.hhplus.tdd.point.application;

import io.hhplus.tdd.point.domain.PointHistory;
import io.hhplus.tdd.point.domain.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetPointHistoryService {

    private final PointHistoryRepository pointHistoryRepository;

    public List<PointHistory> histories(long userId) {
        return pointHistoryRepository.selectAllByUserId(userId);
    }

    public PointHistory history(long userId) {
        return pointHistoryRepository.selectOneByUserId(userId);
    }
}
