package ba.unsa.etf;

import java.util.ArrayList;

public class Subjects {
    private ArrayList<Subject> subjects;

    public Subjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }
}
