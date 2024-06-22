package io.hhplus.tdd.point.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class ChargePointValidatorTest {

    private static final ChargePointValidator validator = new ChargePointValidator();

    @ParameterizedTest
    @ValueSource(longs = {-100, 0, 99})
    void 포인트_충전시_100원_미만일_경우_충전할_수_없다(long amount) {
        // when
        boolean result = validator.validateAmount(amount);

        // then
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @ValueSource(ints = {101, 1101, 1111, 1250})
    void 포인트_충전시_100원_단위가_아닐경우_충전할_수_없다(long amount) {
        // when
        boolean result = validator.validateAmount(amount);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void 포인트_충전시_1_000_000원이_초과할_경우_충전할_수_없다() {
        long amount = 1_000_100L;

        // when
        boolean result = validator.validateAmount(amount);

        // then
        assertThat(result).isFalse();
    }

}
