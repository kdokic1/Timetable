package ba.unsa.etf;

import java.sql.Time;
import java.util.ArrayList;

public class Timetables {
    private ArrayList<Timetable> timetables=new ArrayList<>();
    private User user;

    public Timetables(){
    }

    public Timetables(Timetables timetables){
        this.timetables=timetables.getTimetables();
    }

    public ArrayList<Timetable> getTimetables() {
        return timetables;
    }

    public void setTimetables(ArrayList<Timetable> timetables) {
        this.timetables = timetables;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
