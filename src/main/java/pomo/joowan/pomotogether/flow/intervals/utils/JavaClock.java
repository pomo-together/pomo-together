package pomo.joowan.pomotogether.flow.intervals.utils;

public class JavaClock extends Clock{
    @Override
    public long current() {
        return System.currentTimeMillis();
    }
}
