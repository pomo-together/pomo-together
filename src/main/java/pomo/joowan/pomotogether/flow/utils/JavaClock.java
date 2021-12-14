package pomo.joowan.pomotogether.flow.utils;

public class JavaClock extends Clock{
    @Override
    public long current() {
        return System.currentTimeMillis();
    }
}
