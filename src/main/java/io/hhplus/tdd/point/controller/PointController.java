package io.hhplus.tdd.point.controller;

import io.hhplus.tdd.point.application.ChargePointService;
import io.hhplus.tdd.point.application.UsePointService;
import io.hhplus.tdd.point.domain.PointHistory;
import io.hhplus.tdd.point.domain.UserPoint;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/point")
public class PointController {

    private static final Logger log = LoggerFactory.getLogger(PointController.class);

    private final ChargePointService chargePointService;
    private final UsePointService usePointService;

    /**
     * TODO - 특정 유저의 포인트를 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{id}")
    public UserPoint point(
            @PathVariable long id
    ) {
        return new UserPoint(0, 0, 0);
    }

    /**
     * TODO - 특정 유저의 포인트 충전/이용 내역을 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{userId}/histories")
    public List<PointHistory> history(
            @PathVariable long userId
    ) {
        return List.of();
    }

    /**
     * TODO - 특정 유저의 포인트를 충전하는 기능을 작성해주세요.
     */
    @PatchMapping("{userId}/charge")
    public UserPoint charge(
            @PathVariable long userId,
            @RequestBody long amount
    ) {
        return chargePointService.charge(userId, amount);
    }

    /**
     * TODO - 특정 유저의 포인트를 사용하는 기능을 작성해주세요.
     */
    @PatchMapping("{userId}/use")
    public UserPoint use(
            @PathVariable long userId,
            @RequestBody long amount
    ) {
        return usePointService.usePoint(userId, amount);
    }
}
