package com.tfl.billing;

public class SystemClock implements ClockInterface {
    @Override
    public long getCurrentTime() {
        return System.currentTimeMillis();
    }

}
