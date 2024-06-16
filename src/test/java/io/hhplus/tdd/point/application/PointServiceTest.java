package io.hhplus.tdd.point.application;

import io.hhplus.tdd.point.domain.UserPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 통합 테스트 케이스
 * <p>
 * - 포인트 충전
     * - 100원 단위로 충전이 가능하다.
     * - 100원 미만일 시 충전에 실패한다
     * - 100원 단위가 아닐경우 충전에 실패한다
     * - 1_000_000원이 초과할 경우 충전할 수 없다.
     * - 충전 성공 및 실패 내역을 기록한다
 * - 포인트 사용
     * - 최소 100원 이상 1_000_000원 이하까지 사용할 수 있다.
     * - 사용 성공 내역을 기록한다
     * - 잔액이 부족할 경우 사용할 수 없다.
     * - 사용 실패 내역을 기록한다
 * - 포인트 조회
     * - 포인트 조회에 성공한다
 * - 포인트 내역 조회
     * - 포인트 내역 조회에 성공한다
 */
@SpringBootTest
class PointServiceTest {

    @Autowired
    private PointService pointService;

    @ParameterizedTest
    @ValueSource(ints = {100, 1100, 12300})
    void 포인트_충전시_100원_단위로_충전이_가능하다(int amount) {
        // given
        long pointId = 1L;
        ChargePointCommand command = new ChargePointCommand(1L, amount);

        // when
        UserPoint userPoint = pointService.charge(command);

        // then
        assertEquals(amount, userPoint.point());
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, 0, 99})
    void 포인트_충전시_100원_미만일_경우_충전할_수_없다(long amount) {
        // given
        long pointId = 1L;

        // when & then
        assertThatThrownBy(() -> new ChargePointCommand(pointId, amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("100원 이상 충전 가능합니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {101, 1101, 1111, 1250})
    void 포인트_충전시_100원_단위가_아닐경우_충전할_수_없다(long amount) {
        // given
        long pointId = 1L;

        // when & then
        assertThatThrownBy(() -> new ChargePointCommand(pointId, amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("100원 단위로 충전 가능합니다.");
    }

    @Test
    void 포인트_충전시_1_000_000원이_초과할_경우_충전할_수_없다() {
        // given
        long pointId = 1L;
        long amount = 1_000_100L;

        // when & then
        assertThatThrownBy(() -> new ChargePointCommand(pointId, amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("1,000,000원을 초과하여 충전 할 수 없습니다.");
    }

}
