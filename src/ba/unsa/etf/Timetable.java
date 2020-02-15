package ba.unsa.etf;

import java.time.LocalTime;

public class Timetable {
    private String timetableName;
    private User user;
    private boolean includeSaturday;

    public Timetable(String timetableName, User user, boolean saturday) {
        this.timetableName = timetableName;
        this.user = user;
        this.includeSaturday=saturday;
    }

    public String getTimetableName() {
        return timetableName;
    }

    public void setTimetableName(String timetableName) {
        this.timetableName = timetableName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isIncludeSaturday() {
        return includeSaturday;
    }

    public void setIncludeSaturday(boolean includeSaturday) {
        this.includeSaturday = includeSaturday;
    }

    @Override
    public String toString() {
        return timetableName;
    }
}
