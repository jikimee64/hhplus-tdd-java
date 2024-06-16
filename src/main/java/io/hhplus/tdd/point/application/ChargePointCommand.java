package io.hhplus.tdd.point.application;

import lombok.Getter;

@Getter
public record ChargePointCommand(long pointId, long amount) {

    private static final long MIN_AMOUNT = 100;
    private static final long AMOUNT_UNIT = 100;

    public ChargePointCommand {
        validateAmount(amount);
    }

    private void validateAmount(long amount) {
        if (amount < MIN_AMOUNT) {
            throw new IllegalArgumentException("100원 이상 충전 가능합니다.");
        }

        if (amount % AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException("100원 단위로 충전 가능합니다.");
        }
    }

}
