package io.hhplus.tdd.point.domain;

public class ChargePointValidator {

    private static final long MIN_AMOUNT = 100;
    private static final long MAX_AMOUNT = 1_000_000;
    private static final long AMOUNT_UNIT = 100;

    public boolean validateAmount(long amount) {
        if (amount < MIN_AMOUNT) {
            return false;
        }

        if (amount % AMOUNT_UNIT != 0) {
            return false;
        }

        if (amount > MAX_AMOUNT) {
            return false;
        }

        return true;
    }
}
