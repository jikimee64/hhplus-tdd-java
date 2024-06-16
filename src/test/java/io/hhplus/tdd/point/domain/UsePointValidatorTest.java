package io.hhplus.tdd.point.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UsePointValidatorTest {

    private static final UsePointValidator validator = new UsePointValidator();

    @Test
    void 포인트_사용시_100원_미만일_경우_사용할_수_없다() {
        // given
        long amount = 99;

        // when
        boolean result = validator.validatePoint(amount);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void 포인트_사용시_500_000원을_초과할_경우_사용할_수_없다() {
        // given
        long amount = 500_001;

        // when
        boolean result = validator.validatePoint(amount);

        // then
        assertThat(result).isFalse();
    }

}