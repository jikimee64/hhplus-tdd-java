package io.hhplus.tdd.point.application;

import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReentrantLock;

@Component
public class PointServiceLock {
    private final ReentrantLock lock = new ReentrantLock(true);

    public void getLock() {
        lock.lock();
    }

    public void releaseLock() {
        lock.unlock();
    }

    public boolean isLocked() {
        return lock.isLocked();
    }
}
