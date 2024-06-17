package io.hhplus.tdd.point.application;

import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.infra.persistence.UserPointTable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * 포인트 조회 테스트 케이스
 * <p>
 * - 포인트 조회
 * - 포인트 조회에 성공한다
 */
@SpringBootTest
public class GetPointServiceTest {

    @Autowired
    private GetPointService getPointService;

    @Autowired
    private UserPointTable userPointTable;

    private final long userId = 1L;
    private final long amount = 100L;

    // 오름차순 적용을 위해 시간값 순차적으로 증가
    @BeforeEach
    void setUp() {
        userPointTable.insertOrUpdate(userId, amount);
    }

    @Test
    void 유저에_대한_포인트_조회에_성공한다(){
        // when
        UserPoint userPoint = getPointService.point(userId);

        // then
        assertThat(userPoint.point()).isEqualTo(amount);
    }

    @AfterEach
    void afterAll(){
        userPointTable.clear();
    }

}
