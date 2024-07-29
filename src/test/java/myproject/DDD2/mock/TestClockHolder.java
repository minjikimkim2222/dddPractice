package myproject.DDD2.mock;

import lombok.RequiredArgsConstructor;
import myproject.DDD2.common.service.port.ClockHolder;

import java.time.Clock;

@RequiredArgsConstructor
public class TestClockHolder implements ClockHolder {

    private final long millis;

    @Override
    public long millis() {
        return millis;
    }
}
