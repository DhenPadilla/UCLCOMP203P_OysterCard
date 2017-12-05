package com.tfl.billing;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class JourneyStart extends JourneyEvent {

    public JourneyStart(UUID cardId, UUID readerId) {
        super(cardId, readerId);
    }
}
