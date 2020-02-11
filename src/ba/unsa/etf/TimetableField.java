package ba.unsa.etf;

import java.sql.Time;

public class TimetableField {
    private User user;
    private Timetable timetable;
    private Subject subject;
    private TimeClass starts;
    private TimeClass ends;
    private Day day;
    private int ordinalNumber;

    public TimetableField(User user, Timetable timetable, Subject subject, TimeClass starts,TimeClass ends, Day day, int ordinalNumber) {
        this.user = user;
        this.timetable = timetable;
        this.subject = subject;
        this.day = day;
        this.ordinalNumber = ordinalNumber;
        this.starts=new TimeClass(starts);
        this.ends=new TimeClass(ends);
    }

    public TimetableField(){}


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public TimeClass getStarts() {
        return starts;
    }

    public void setStarts(TimeClass starts) {
        this.starts = starts;
    }

    public TimeClass getEnds() {
        return ends;
    }

    public void setEnds(TimeClass ends) {
        this.ends = ends;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public int getOrdinalNumber() {
        return ordinalNumber;
    }

    public void setOrdinalNumber(int ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }
}
