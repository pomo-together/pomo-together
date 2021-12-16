package pomo.joowan.pomotogether.pomosession.utils;

public class JavaClock extends Clock{
    @Override
    public long currentTimeSeconds() {
        return System.currentTimeMillis()/1000;
    }
}
