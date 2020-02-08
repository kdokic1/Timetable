package ba.unsa.etf;

public class Subject {
    private String subjectName;
    private String teacher;
    private String classroom;

    public Subject(String subjectName, String teacher, String classroom) {
        this.subjectName = subjectName;
        this.teacher = teacher;
        this.classroom = classroom;
    }

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
}
