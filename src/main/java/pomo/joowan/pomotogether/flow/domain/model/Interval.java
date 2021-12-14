package pomo.joowan.pomotogether.flow.domain.model;

import pomo.joowan.pomotogether.flow.utils.Clock;

public class Interval {
    private long id;
    private long flowId;
    private long timeLimit;
    private int order;
    private String name;
    private Clock clock;

    public Interval(long flowId, long timeLimit, int order, String name, Clock clock) {
        this.flowId = flowId;
        this.timeLimit = timeLimit;
        this.order = order;
        this.name = name;
        this.clock = clock;
    }

    public String getName() {
        return this.name;
    }

    public long getTimeLimit() {
        return this.timeLimit;
    }

    public boolean isOver(long startTime) {
        long currentTime = clock.current();
        return currentTime >= (startTime + timeLimit);
    }
}
