package pomo.joowan.pomotogether.flow.domain.model;

import pomo.joowan.pomotogether.flow.exceptions.TimerIsNotPausedException;
import pomo.joowan.pomotogether.flow.exceptions.TimerIsNotStoppedException;
import pomo.joowan.pomotogether.flow.exceptions.TimerIsNotWorkingException;
import pomo.joowan.pomotogether.flow.utils.Clock;

public class Timer {
    private final Clock clock;

    private long startTime;
    private long elapsedMilliSeconds;
    private TimerState state;

    public Timer(Clock clock) {
        this.clock = clock;
        this.startTime = 0;
        this.elapsedMilliSeconds = 0;
        this.state = TimerState.STOPPED;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public long getElapsedMilliSeconds() {
        return this.elapsedMilliSeconds;
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
        this.elapsedMilliSeconds = this.clock.current() - this.startTime;
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
        this.startTime = 0;
        this.elapsedMilliSeconds = 0;
        this.state = TimerState.STOPPED;
    }
}
