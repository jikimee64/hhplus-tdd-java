package io.hhplus.tdd.point.application;

import io.hhplus.tdd.point.domain.PointHistory;
import io.hhplus.tdd.point.infra.persistence.PointHistoryTable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static io.hhplus.tdd.point.domain.TransactionType.*;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * 포인트 내역 조회 테스트 케이스
 * <p>
 * - 포인트 내역 조회
 * - 포인트 내역 조회에 성공한다
 */
@SpringBootTest
class GetPointHistoryServiceTest {

    @Autowired
    private GetPointHistoryService getPointHistoryService;

    @Autowired
    private PointHistoryTable pointHistoryTable;

    private final long userId = 1L;
    private final long failAmount = 99L;
    private final long successAmount = 100L;

    // 오름차순 적용을 위해 시간값 순차적으로 증가
    @BeforeEach
    void setUp() {
        pointHistoryTable.insert(userId, successAmount, CHARGE_SUCCESS, System.currentTimeMillis());
        pointHistoryTable.insert(userId, failAmount, CHARGE_FAIL, System.currentTimeMillis() + 1);
        pointHistoryTable.insert(userId, successAmount, USE_SUCCESS, System.currentTimeMillis() + 2);
        pointHistoryTable.insert(userId, failAmount, USE_FAIL, System.currentTimeMillis() + 3);
    }

    @Test
    void 유저에_대한_포인트_전체_내역_조회에_성공한다() {
        // when
        List<PointHistory> histories = getPointHistoryService.histories(userId);

        // then
        assertThat(histories).hasSize(4)
                .extracting("userId", "amount", "type")
                .containsExactly(
                        tuple(userId, failAmount, USE_FAIL),
                        tuple(userId, successAmount, USE_SUCCESS),
                        tuple(userId, failAmount, CHARGE_FAIL),
                        tuple(userId, successAmount, CHARGE_SUCCESS)
                );
    }


    @Test
    void 유저에_대한_포인트_내역_중_가장_최신_데이터_한개_조회에_성공한다() {
        // when
        PointHistory history = getPointHistoryService.history(userId);

        // then
        assertAll(
                () -> assertThat(history.userId()).isEqualTo(userId),
                () -> assertThat(history.amount()).isEqualTo(failAmount),
                () -> assertThat(history.type()).isEqualTo(USE_FAIL)
        );
    }

    @AfterEach
    void afterAll() {
        pointHistoryTable.clear();
    }
}
