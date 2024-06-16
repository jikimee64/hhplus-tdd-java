package io.hhplus.tdd.point.domain;

public record UserPoint(long userId, long point, long updateMillis) {

    public static UserPoint empty(long userId) {
        return new UserPoint(userId, 0, System.currentTimeMillis());
    }

    public UserPoint usePoint(long point) {
        if(this.point < point){
            throw new IllegalArgumentException("남은 포인트 잔액이 부족하여 포인트를 사용할 수 없습니다.");
        }
        return new UserPoint(userId, this.point - point, System.currentTimeMillis());
    }
}
