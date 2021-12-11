package pomo.joowan.pomotogether.timer.domain;

import pomo.joowan.pomotogether.timer.exceptions.TimerIsNotPausedException;
import pomo.joowan.pomotogether.timer.exceptions.TimerIsNotStoppedException;
import pomo.joowan.pomotogether.timer.exceptions.TimerIsNotWorkingException;
import pomo.joowan.pomotogether.timer.utils.Clock;

public class Timer {
    private final Clock clock;

    private long startTime;
    private long elapsedSeconds;
    private TimerState state;

    public Timer(Clock clock) {
        this.clock = clock;
        this.startTime = 0;
        this.elapsedSeconds = 0;
        this.state = TimerState.STOPPED;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public TimerState getState() {
        return this.state;
    }

    public void start() {
        if (this.state != TimerState.STOPPED) {
            throw new TimerIsNotStoppedException();
        }
        this.startTime = clock.current();
        this.state = TimerState.WORKING;
    }

    public void pause() {
        if (this.state != TimerState.WORKING) {
            throw new TimerIsNotWorkingException();
        }
        this.elapsedSeconds = this.clock.current() - this.startTime;
        this.startTime = 0;
        this.state = TimerState.PAUSED;
    }

    public void resume() {
        if (this.state != TimerState.PAUSED) {
            throw new TimerIsNotPausedException();
        }
        this.startTime = this.clock.current();
        this.state = TimerState.WORKING;
    }

    public void reset() {

    }
}
