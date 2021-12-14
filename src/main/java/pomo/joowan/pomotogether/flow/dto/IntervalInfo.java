package pomo.joowan.pomotogether.flow.dto;

public class IntervalInfo {
    private String name;
    private long timeLimit;

    public IntervalInfo(String name, long timeLimit) {
        this.name = name;
        this.timeLimit = timeLimit;
    }

    public String getName() {
        return this.name;
    }

    public long getTimeLimit() {
        return this.timeLimit;
    }
}
