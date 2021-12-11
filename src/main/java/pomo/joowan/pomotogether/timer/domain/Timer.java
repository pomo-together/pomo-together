package pomo.joowan.pomotogether.timer.domain;

import pomo.joowan.pomotogether.timer.exceptions.TimerIsAlreadyStartedException;
import pomo.joowan.pomotogether.timer.utils.Clock;
import pomo.joowan.pomotogether.timer.utils.JavaClock;

public class Timer {
    private long startTime;
    private long elapsedSeconds;
    private final Clock clock;

    public Timer(Clock clock) {
        this.clock = clock;
        this.startTime = 0;
        this.elapsedSeconds = 0;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void start() {
        if (this.startTime != 0) {
            throw new TimerIsAlreadyStartedException();
        }
        this.startTime = clock.current();
    }
}
