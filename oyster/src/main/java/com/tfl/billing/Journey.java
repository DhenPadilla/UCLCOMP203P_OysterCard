package com.tfl.billing;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Journey {

    private final JourneyEvent start;
    private final JourneyEvent end;

    private Date startOfJourneyTime;
    private Date endOfJourneyTime;

    public UUID originId() {
        return start.readerId();
    }

    public UUID destinationId() {
        return end.readerId();
    }

    public String formattedStartTime() {
    //    if (startOfJourneyTime == null) {
    //        return format(start.time());
    //    }
    //    else {
            return format(startOfJourneyTime.getTime());
    //    }
    }

    public String formattedEndTime() {
        return format(endOfJourneyTime.getTime());
    }

    public Date startTime() {
    //    if (startOfJourneyTime == null) {
    //        return new Date(start.time());
    //    }
    //    else {
            return  startOfJourneyTime;
    //    }
    }

    public Date endTime() {
        //return new Date(end.time());
        return endOfJourneyTime;
    }

    public int durationSeconds() {
   //     if (startOfJourneyTime == null) {
   //         return (int) ((end.time() - start.time()) / 1000);
   //     }
   //     else {
            return (int) ((endOfJourneyTime.getTime() - startOfJourneyTime.getTime()));
   //     }
    }

    public String durationMinutes() {
        return "" + durationSeconds() / 60 + ":" + durationSeconds() % 60;
    }

    private String format(long time) {
        return SimpleDateFormat.getInstance().format(new Date(time));
    }


    /*
    public Journey(JourneyEvent start, JourneyEvent end) {
        this.start = start;
        this.end = end;
    }
    */

    private Journey(JourneyBuilder journeyBuilder) {
        this.start = journeyBuilder.start;
        this.end = journeyBuilder.end;
        this.startOfJourneyTime = journeyBuilder.startOfJourneyTime;
        this.endOfJourneyTime = journeyBuilder.endOfJourneyTime;
    }


    //Builder class
    public static class JourneyBuilder {
        //Required Parameters
        private JourneyEvent start;
        private JourneyEvent end;

        //Optional Parameters
        private Date startOfJourneyTime;
        private Date endOfJourneyTime;

        public JourneyBuilder(JourneyEvent start, JourneyEvent end){
            this.start = start;
            this.end = end;
        }

        public JourneyBuilder setStartTime(int hour) {
            Calendar cal = Calendar.getInstance();
            cal.set(2017, 12, 01, hour, 20);
            Date date = cal.getTime();
            this.startOfJourneyTime = date;
            return this;
        }

        public JourneyBuilder setEndTime(int hour, int minutes) {
            Calendar cal = Calendar.getInstance();
            cal.set(2017, 12, 01, hour, minutes);
            Date date = cal.getTime();
            this.endOfJourneyTime = date;
            return this;
        }

        public Journey build() {
            return new Journey(this);
        }
    }

}
