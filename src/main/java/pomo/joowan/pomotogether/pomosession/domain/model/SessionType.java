package pomo.joowan.pomotogether.pomosession.domain.model;

public enum SessionType {
    WORK("work"), BREAK("break");

    private String value;

    SessionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
