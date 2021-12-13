package pomo.joowan.pomotogether.flow.intervals.domain;

import pomo.joowan.pomotogether.flow.intervals.utils.Clock;

public class Interval {
    private long id;
    private long timeLimit;
    private String name;
    private Clock clock;

    public Interval(long timeLimit, String name, Clock clock) {
        this.timeLimit = timeLimit;
        this.name = name;
        this.clock = clock;
    }

    public long getTimeLimit() {
        return this.timeLimit;
    }

    public boolean isOver(long startTime) {
        long currentTime = clock.current();
        return currentTime >= (startTime + timeLimit);
    }
}
