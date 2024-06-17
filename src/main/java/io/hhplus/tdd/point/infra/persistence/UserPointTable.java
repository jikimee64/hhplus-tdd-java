package io.hhplus.tdd.point.infra.persistence;

import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.domain.UserPointRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 해당 Table 클래스는 변경하지 않고 공개된 API 만을 사용해 데이터를 제어합니다.
 */
@Component
public class UserPointTable implements UserPointRepository {

    private final Map<Long, UserPoint> table = new HashMap<>();

    public UserPoint selectById(Long userId) {
        throttle(200);
        return table.getOrDefault(userId, UserPoint.empty(userId));
    }

    public UserPoint insertOrUpdate(long userId, long amount) {
        throttle(300);
        UserPoint userPoint = new UserPoint(userId, amount, System.currentTimeMillis());
        table.put(userId, userPoint);
        return userPoint;
    }

    private void throttle(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep((long) (Math.random() * millis));
        } catch (InterruptedException ignored) {

        }
    }

    // 조회 테스트를 위한 API 추가
    public void clear() {
        table.clear();
    }
}
