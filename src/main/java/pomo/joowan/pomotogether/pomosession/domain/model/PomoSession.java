package pomo.joowan.pomotogether.pomosession.domain.model;

import pomo.joowan.pomotogether.pomosession.exceptions.InvalidPomoSessionStateException;
import pomo.joowan.pomotogether.pomosession.exceptions.NotEnoughElapsedTimeException;

public class PomoSession {
    private final long SECONDS_PER_MINUTE = 60;

    private long startTimeSeconds;
    private long elapsedSeconds;
    private long limitSeconds;
    private SessionState sessionState;
    private SessionType sessionType;

    public PomoSession() {
        this.startTimeSeconds = 0;
        this.elapsedSeconds = 0;
        this.limitSeconds = 0;
        this.sessionState = SessionState.STOPPED;
        this.sessionType = SessionType.WORK;
    }

    public long getStartTimeSeconds() {
        return this.startTimeSeconds;
    }

    public long getElapsedSeconds() {
        return this.elapsedSeconds;
    }

    public long getLimitSeconds() {
        return this.limitSeconds;
    }

    public SessionState getSessionState() {
        return this.sessionState;
    }

    public SessionType getSessionType() {
        return this.sessionType;
    }

    public void start(long startTimeSeconds, long limitMinutes) {
        if (this.sessionState != SessionState.STOPPED) {
            throw new InvalidPomoSessionStateException();
        }

        this.startTimeSeconds = startTimeSeconds;
        this.elapsedSeconds = 0;
        this.limitSeconds = limitMinutes * SECONDS_PER_MINUTE;
        this.sessionState = SessionState.WORKING;
    }

    public void finish(long endTimeSeconds) {
        if (this.sessionState != SessionState.WORKING) {
            throw new InvalidPomoSessionStateException();
        }

        this.elapsedSeconds += endTimeSeconds - this.startTimeSeconds;
        if (this.elapsedSeconds < this.limitSeconds) {
            throw new NotEnoughElapsedTimeException();
        }

        this.sessionType = (this.sessionType == SessionType.WORK) ? SessionType.BREAK : SessionType.WORK;
        this.sessionState = SessionState.STOPPED;
    }

    public void pause(long currentTimeSeconds) {
        if (this.sessionState != SessionState.WORKING) {
            throw new InvalidPomoSessionStateException();
        }

        this.elapsedSeconds += currentTimeSeconds - this.startTimeSeconds;
        this.sessionState = SessionState.PAUSED;
    }

    public void resume(long currentTimeSeconds) {

    }
}
