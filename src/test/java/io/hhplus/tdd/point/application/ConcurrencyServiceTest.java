package io.hhplus.tdd.point.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
public class ConcurrencyServiceTest {

    @Autowired
    private ChargePointService chargePointService;

    @Autowired
    private UsePointService usePointService;

    @Autowired
    private GetPointService getPointService;

    @Test
    void test() throws InterruptedException {
        // given
        chargePointService.charge(1L, 10000);

        // when
        // 모든 작업이 동시에 시작되도록 함
        CountDownLatch startLatch = new CountDownLatch(1);
        // 모든 작업이 완료될 때 까지 대기
        CountDownLatch endLatch = new CountDownLatch(3);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Runnable work1 = () -> executeTask(startLatch, endLatch, () -> chargePointService.charge(1L, 1000));
        Runnable work2 = () -> executeTask(startLatch, endLatch, () -> usePointService.usePoint(1L, 100));
        Runnable work3 = () -> executeTask(startLatch, endLatch, () -> chargePointService.charge(1L, 1000));

        // 스레드 풀에 작업 제출
        executorService.execute(work1);
        executorService.execute(work2);
        executorService.execute(work3);

        // 모든 작업을 동시에 시작
        startLatch.countDown();

        // 모든 작업이 완료될 때까지 대기
        endLatch.await(10, TimeUnit.SECONDS);

        // 모든 작업 종료
        executorService.shutdown();
        // 모든 작업 완료될 떄 까지 최대 10초 동안 대기
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        // then
        long expectedPoints = 10000 + 1000 - 100 + 1000;
        assertThat(getPointService.point(1L).point()).isEqualTo(expectedPoints);
    }

    private void executeTask(CountDownLatch startLatch, CountDownLatch endLatch, Runnable task) {
        try {
            startLatch.await();
            task.run();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            endLatch.countDown();
        }
    }

}
