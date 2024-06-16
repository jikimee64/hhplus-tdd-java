package io.hhplus.tdd.point.application;

import io.hhplus.tdd.point.domain.UserPoint;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

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

    @ParameterizedTest
    @ValueSource(longs = {100, 500_000})
    void 포인트_사용시_100원_이상_500_000원_이하로_사용할_수_있다(long amount) {
        // given
        long userId = 1L;
        long chargeAmount = 1_000_000;
        UserPoint userPoint = chargePointService.charge(userId, chargeAmount);

        // when
        boolean result = usePointService.usePoint(userPoint.userId(), amount);

        // then
        assertThat(result).isTrue();
    }

}
