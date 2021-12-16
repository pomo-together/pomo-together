package pomo.joowan.pomotogether.pomosession.domain.model;

public enum SessionState {
    WORKING("working"), PAUSED("paused"), STOPPED("stopped");

    private final String value;

    SessionState(String value) {
        this.value = value;
    }

    public String getState() {
        return this.value;
    }
}
