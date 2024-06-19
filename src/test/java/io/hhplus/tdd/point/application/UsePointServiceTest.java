package io.hhplus.tdd.point.application;

import io.hhplus.tdd.point.domain.PointHistory;
import io.hhplus.tdd.point.domain.TransactionType;
import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.infra.persistence.UserPointTable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static io.hhplus.tdd.point.domain.TransactionType.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * 포인트 사용 테스트 케이스
 * <p>
 * - 포인트 사용
 * - 100원 이상 500_000원 이하까지 사용할 수 있다.
 * - 100원 미만 500_000원 초과일 경우 사용할 수 있다.
 * - 잔액이 부족할 경우 사용할 수 없다.
 * - 사용 성공 및 실패 내역을 기록한다
 */
@SpringBootTest
class UsePointServiceTest {

    @Autowired
    private ChargePointService chargePointService;

    @Autowired
    private UsePointService usePointService;

    @Autowired
    private GetPointHistoryService getPointHistoryService;

    @Autowired
    private UserPointTable userPointTable;

    @ParameterizedTest
    @ValueSource(longs = {100, 500_000})
    void 포인트_사용시_100원_이상_500_000원_이하로_사용할_수_있다(long amount) {
        // given
        long userId = 1L;
        long chargeAmount = 1_000_000;
        UserPoint userPoint = chargePointService.charge(userId, chargeAmount);

        // when
        UserPoint usedUserPoint = usePointService.usePoint(userPoint.userId(), amount);

        // then
        assertAll(
                () -> assertThat(usedUserPoint.point()).isEqualTo(chargeAmount - amount),
                () -> assertThatPointHistory(amount, userId, USE_SUCCESS)
        );
        userPointTable.clear();
    }

    @ParameterizedTest
    @ValueSource(longs = {99, 500_001})
    void 포인트_사용시_100원_미만_500_000원_초과일_경우_사용할_수_없다(long amount) {
        // given
        long userId = 1L;

        // when
        usePointService.usePoint(userId, amount);

        // then
        assertAll(
                () -> assertThatPointHistory(amount, userId, USE_FAIL)
        );
    }

    @Test
    void 포인트_사용시_남은_포인트_잔액이_부족한경우_사용할_수_없다() {
        // given
        long userId = 1L;
        long chargeAmount = 1000;
        long useAmount = 1001;
        UserPoint chargedUserPoint = chargePointService.charge(userId, chargeAmount);

        // when
        UserPoint userPoint = usePointService.usePoint(chargedUserPoint.userId(), useAmount);

        // then
        assertAll(
                () -> assertThat(userPoint.point()).isEqualTo(chargeAmount),
                () -> assertThatPointHistory(useAmount, userId, USE_FAIL)
        );
        userPointTable.clear();
    }

    private void assertThatPointHistory(long amount, long userId, TransactionType type) {
        PointHistory pointHistory = getPointHistoryService.history(userId);
        assertAll(
                () -> assertThat(pointHistory.userId()).isEqualTo(userId),
                () -> assertThat(pointHistory.amount()).isEqualTo(amount),
                () -> assertThat(pointHistory.type()).isEqualTo(type)
        );
    }

}
