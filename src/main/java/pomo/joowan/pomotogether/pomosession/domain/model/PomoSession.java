package pomo.joowan.pomotogether.pomosession.domain.model;

import pomo.joowan.pomotogether.pomosession.exceptions.InvalidPomoSessionStateException;

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

    public long getLimitSeconds() {
        return this.limitSeconds;
    }

    public SessionState getSessionState() {
        return this.sessionState;
    }

    public void start(long startTimeSeconds, long limitMinutes) {
        if (this.sessionState != SessionState.STOPPED) {
            throw new InvalidPomoSessionStateException();
        }
        this.startTimeSeconds = startTimeSeconds;
        this.limitSeconds = limitMinutes * SECONDS_PER_MINUTE;
        this.sessionState = SessionState.WORKING;
    }
}
