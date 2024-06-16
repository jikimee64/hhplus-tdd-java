package io.hhplus.tdd.point.infra.persistence;

import io.hhplus.tdd.point.domain.PointHistory;
import io.hhplus.tdd.point.domain.PointHistoryRepository;
import io.hhplus.tdd.point.domain.TransactionType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 해당 Table 클래스는 변경하지 않고 공개된 API 만을 사용해 데이터를 제어합니다.
 */
@Component
public class PointHistoryTable implements PointHistoryRepository {
    private final List<PointHistory> table = new ArrayList<>();
    private long cursor = 1;

    public PointHistory insert(long userId, long amount, TransactionType type, long updateMillis) {
        throttle(300L);
        PointHistory pointHistory = new PointHistory(cursor++, userId, amount, type, updateMillis);
        table.add(pointHistory);
        return pointHistory;
    }

    /**
     *  order by 추가
     *  - userId로 조회한 데이터를 updateMillis 기준으로 내림차순 정렬하여 반환합니다.
     */
    public List<PointHistory> selectAllByUserId(long userId) {
        return table.stream().filter(pointHistory -> pointHistory.userId() == userId)
                .sorted((o1, o2) -> Long.compare(o2.updateMillis(), o1.updateMillis()))
                .toList();
    }

    // 포인트 성공 및 실패 내역을 테스트에서 검증하기 위한 API 추가
    public PointHistory selectOneByUserId(long userId) {
        return table.stream().filter(pointHistory -> pointHistory.userId() == userId)
                .min((o1, o2) -> Long.compare(o2.updateMillis(), o1.updateMillis()))
                .orElseThrow(() -> new IllegalArgumentException("Not found PointHistory By userId: " + userId));
    }

    private void throttle(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep((long) (Math.random() * millis));
        } catch (InterruptedException ignored) {

        }
    }
}
