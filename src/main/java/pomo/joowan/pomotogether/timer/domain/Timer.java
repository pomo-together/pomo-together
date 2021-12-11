package pomo.joowan.pomotogether.timer.domain;

import pomo.joowan.pomotogether.timer.exceptions.TimerIsAlreadyStartedException;
import pomo.joowan.pomotogether.timer.exceptions.TimerIsAlreadyStoppedException;
import pomo.joowan.pomotogether.timer.utils.Clock;

public class Timer {
    private final Clock clock;

    private long startTime;
    private long elapsedSeconds;

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

    public void pause() {
        if (this.startTime == 0) {
            throw new TimerIsAlreadyStoppedException();
        }
        this.elapsedSeconds = this.clock.current() - this.startTime;
        this.startTime = 0;
    }
}
