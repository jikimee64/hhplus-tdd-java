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

import static io.hhplus.tdd.point.domain.TransactionType.CHARGE_FAIL;
import static io.hhplus.tdd.point.domain.TransactionType.CHARGE_SUCCESS;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * 포인트 충천 테스트 케이스
 * <p>
 * - 포인트 충전
 * - 100원 단위로 충전이 가능하다.
 * - 100원 미만일 시 충전에 실패한다
 * - 100원 단위가 아닐경우 충전에 실패한다
 * - 1_000_000원이 초과할 경우 충전할 수 없다.
 * - 충전 성공 및 실패 내역을 기록한다
 */
@SpringBootTest
class ChargePointServiceTest {

    @Autowired
    private ChargePointService chargePointService;

    @Autowired
    private GetPointHistoryService getPointHistoryService;

    @Autowired
    private UserPointTable userPointTable;

    @ParameterizedTest
    @ValueSource(longs = {100, 1100, 12300})
    void 포인트_충전시_100원_단위로_충전이_가능하다(long amount) {
        // given
        long userId = 1L;

        // when
        UserPoint userPoint = chargePointService.charge(userId, amount);

        // then
        assertAll(
                () -> assertThat(amount).isEqualTo(userPoint.point()),
                () -> assertThatPointHistory(amount, userId, CHARGE_SUCCESS)
        );
        userPointTable.clear();
    }

    @ParameterizedTest
    @ValueSource(longs = {-100, 0, 99})
    void 포인트_충전시_100원_미만일_경우_충전할_수_없다(long amount) {
        // given
        long userId = 1L;

        // when
        UserPoint userPoint = chargePointService.charge(userId, amount);

        // then
        assertAll(
                () -> assertThat(userPoint.point()).isEqualTo(0L),
                () -> assertThatPointHistory(amount, userId, CHARGE_FAIL)
        );
        userPointTable.clear();
    }


    @ParameterizedTest
    @ValueSource(ints = {101, 1101, 1111, 1250})
    void 포인트_충전시_100원_단위가_아닐경우_충전할_수_없다(long amount) {
        // given
        long userId = 1L;

        // when
        UserPoint userPoint = chargePointService.charge(userId, amount);

        // then
        assertAll(
                () -> assertThat(userPoint.point()).isEqualTo(0L),
                () -> assertThatPointHistory(amount, userId, CHARGE_FAIL)
        );
        userPointTable.clear();
    }

    @Test
    void 포인트_충전시_1_000_000원이_초과할_경우_충전할_수_없다() {
        // given
        long userId = 1L;
        long amount = 1_000_100L;

        // when
        UserPoint userPoint = chargePointService.charge(userId, amount);

        // then
        assertAll(
                () -> assertThat(userPoint.point()).isEqualTo(0L),
                () -> assertThatPointHistory(amount, userId, CHARGE_FAIL)
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
