package com.tfl.billing;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class JourneyStart extends JourneyEvent {

    public JourneyStart(UUID cardId, UUID readerId, ClockInterface clock) {
        super(cardId, readerId, clock);
    }

    public JourneyStart(UUID cardId, UUID readerId) {
        super(cardId, readerId);
    }

    //Used for testing
    public void setTime(long time) {
       this.time =  time;
    }
}
