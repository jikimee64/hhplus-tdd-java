package io.hhplus.tdd.point.application;

public record ChargePointCommand(long userId, long amount) {

    private static final long MIN_AMOUNT = 100;
    private static final long MAX_AMOUNT = 1_000_000;
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

        if (amount > MAX_AMOUNT) {
            throw new IllegalArgumentException("1,000,000원을 초과하여 충전 할 수 없습니다.");
        }
    }

}
