package io.hhplus.tdd.point.application;

import lombok.Getter;

@Getter
public class ChargePointCommand {

    private final long pointId;

    private final long amount;

    public ChargePointCommand(long pointId, long amount) {
        validateAmount(amount);
        this.pointId = pointId;
        this.amount = amount;
    }

    private void validateAmount(long amount) {
        if(amount < 100){
            throw new IllegalArgumentException("100원 이상 충전 가능합니다.");
        }
    }

}
