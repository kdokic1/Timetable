package ba.unsa.etf;

public class Timetable {
    private String timetableName;
    private User user;

    public Timetable(String timetableName, User user) {
        this.timetableName = timetableName;
        this.user = user;
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

    @Override
    public String toString() {
        return timetableName;
    }
}
