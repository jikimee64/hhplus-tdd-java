package io.hhplus.tdd.point.domain;

public interface UserPointRepository {
    UserPoint selectById(Long userId);
    UserPoint insertOrUpdate(long userId, long amount);
}
