package io.hhplus.tdd.point.application;

import io.hhplus.tdd.point.application.exception.UsePointFailException;
import io.hhplus.tdd.point.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsePointService {

    private final UserPointRepository userPointRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final PointServiceLock pointServiceLock;

    public UserPoint usePoint(long userId, long amount) {
        pointServiceLock.getLock();
        try {
            UserPoint userPoint = userPointRepository.selectById(userId);

            UsePointValidator usePointValidator = new UsePointValidator();
            if (!usePointValidator.validatePoint(amount)) {
                pointHistoryRepository.insert(
                        userId, amount, TransactionType.USE_FAIL, System.currentTimeMillis()
                );
                return userPoint;
            }
            UserPoint usedUserPoint;

            usedUserPoint = usePoint(userId, amount, userPoint);
            userPointRepository.insertOrUpdate(usedUserPoint.userId(), usedUserPoint.point());
            return usedUserPoint;
        } finally {
            pointServiceLock.releaseLock();
        }
    }

    /**
     * - 포인트 사용시
     * - 사용할 수 있는 경우 사용 후 남은 포인트를 반환한다.
     * - 사용할 수 없는 경우 기존 포인트를 반환한다.
     */
    private UserPoint usePoint(long userId, long amount, UserPoint userPoint) {
        UserPoint usedUserPoint = null;
        try {
            usedUserPoint = userPoint.usePoint(amount);
            pointHistoryRepository.insert(
                    userId, amount, TransactionType.USE_SUCCESS, System.currentTimeMillis()
            );
        } catch (UsePointFailException ex) {
            pointHistoryRepository.insert(
                    userId, amount, TransactionType.USE_FAIL, System.currentTimeMillis()
            );
            return userPoint;
        }
        return usedUserPoint;
    }

}
