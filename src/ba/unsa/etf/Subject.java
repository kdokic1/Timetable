package ba.unsa.etf;

public class Subject {
    private String subjectName;
    private String teacher;
    private String classroom;
    private User user;

    public Subject(String subjectName, String teacher, String classroom, User user) {
        this.subjectName = subjectName;
        this.teacher = teacher;
        this.classroom = classroom;
        this.user = user;
    }

    public Subject (){}

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return subjectName;
    }
}
