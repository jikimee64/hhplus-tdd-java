package io.hhplus.tdd.point.domain;

public class UsePointValidator {

    private static final long MIN_AMOUNT = 100;
    private static final long MAX_AMOUNT = 500_000;

    public boolean validatePoint(long amount) {
        if (amount < MIN_AMOUNT) {
            return false;
        }

        if (amount > MAX_AMOUNT) {
            return false;
        }

        return true;
    }

}
