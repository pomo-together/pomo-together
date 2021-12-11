package pomo.joowan.pomotogether.timer.utils;

public class JavaClock extends Clock{
    @Override
    public long current() {
        return System.currentTimeMillis();
    }
}
