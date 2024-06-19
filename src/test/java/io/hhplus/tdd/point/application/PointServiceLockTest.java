package io.hhplus.tdd.point.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class PointServiceLockTest {

    @Autowired
    private PointServiceLock pointServiceLock;

    @Test
    void lock_획득_성공() {
        // when
        pointServiceLock.getLock();

        // then
        assertThat(pointServiceLock.isLocked()).isTrue();
    }

    @Test
    void lock_해제_성공() {
        // given
        pointServiceLock.getLock();

        // when
        pointServiceLock.releaseLock();

        assertThat(pointServiceLock.isLocked()).isFalse();
    }

}
