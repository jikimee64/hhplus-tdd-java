package io.hhplus.tdd.point.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class UserPointTest {

    @Test
    void 사용자_포인트_감소() {
        // given
        UserPoint userPoint = new UserPoint(1L, 1000L, System.currentTimeMillis());
        long usePoint = 100L;

        // when
        UserPoint result = userPoint.usePoint(usePoint);

        // then
        assertThat(900L).isEqualTo(result.point());
    }

}