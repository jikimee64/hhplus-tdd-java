package io.hhplus.tdd.point.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class UserPointTest {

    @Test
    void 사용자_포인트_사용_성공() {
        // given
        UserPoint userPoint = new UserPoint(1L, 1000L, System.currentTimeMillis());
        long usePoint = 100L;

        // when
        UserPoint result = userPoint.usePoint(usePoint);

        // then
        assertThat(900L).isEqualTo(result.point());
    }

    @Test
    void 사용자_포인트_사용_실패(){
        // given
        UserPoint userPoint = new UserPoint(1L, 1000L, System.currentTimeMillis());
        long usePoint = 1001L;

        // when & then
        assertThatThrownBy(() -> userPoint.usePoint(usePoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("남은 포인트 잔액이 부족하여 포인트를 사용할 수 없습니다.");

    }

}