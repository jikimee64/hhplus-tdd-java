package io.hhplus.tdd.point.application;

import io.hhplus.tdd.point.domain.UserPoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 통합 테스트 케이스
 * <p>
 * - 포인트 충전
 * - 100원 단위로 충전이 가능하다.
 * - 100원 미만일 시 충전에 실패한다
 * - 최대 1_000_000원까지 충전 가능하다.
 * - 충전 성공 내역을 기록한다
 * - 1_000_000원을 초과한 경우 충전할 수 없다.
 * - 충전 실패 내역을 기록한다
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

    @Test
    void 포인트_충전시_100원_단위로_충전이_가능하다() {
        // given
        long pointId = 1L;
        long amount = 100L;

        // when
        UserPoint userPoint = pointService.charge(pointId, amount);

        // then
        assertEquals(100L, userPoint.point());
    }

}