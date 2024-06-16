package io.hhplus.tdd.point.domain;

import io.hhplus.tdd.point.application.exception.UsePointFailException;

public record UserPoint(long userId, long point, long updateMillis) {

    public static UserPoint empty(long userId) {
        return new UserPoint(userId, 0, System.currentTimeMillis());
    }

    public UserPoint usePoint(long point) {
        if(this.point < point){
            throw new UsePointFailException();
        }
        return new UserPoint(userId, this.point - point, System.currentTimeMillis());
    }
}
