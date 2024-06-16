package io.hhplus.tdd.point.domain;

public record UserPoint(long userId, long point, long updateMillis) {

    public static UserPoint empty(long userId) {
        return new UserPoint(userId, 0, System.currentTimeMillis());
    }

    public UserPoint usePoint(long point) {
        return new UserPoint(userId, this.point - point, System.currentTimeMillis());
    }
}
