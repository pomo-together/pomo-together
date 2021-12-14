package pomo.joowan.pomotogether.flow.domain.model;

import pomo.joowan.pomotogether.flow.dto.IntervalInfo;
import pomo.joowan.pomotogether.flow.utils.Clock;

public class Flow {
    private long id;
    private String name;

    public Flow(String name) {
        this.name = name;
    }

    public Interval createIntervalWith(int order, Clock clock, IntervalInfo info) {
        return new Interval(this.id, info.getTimeLimit(), order, info.getName(), clock);
    }
}
