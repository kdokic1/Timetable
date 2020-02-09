package ba.unsa.etf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SubjectDAO {
    private static SubjectDAO instance = null;
    private Connection conn;

    public Connection getConn() {
        return conn;
    }

    private PreparedStatement getAllSubjectsForUserQuery, getUserByUsernameQuery, addNewSubjectQuery, removeSubjectQuery, editSubjectQuery;

    public static SubjectDAO getInstance() {
        if (instance == null) instance = new SubjectDAO(); //samo jedna instanca baze, da bi postojao samo jedan ulaz
        return instance;
    }

    private SubjectDAO(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:timetable.db"); //prvo sve konektujemo sa bazom

            try{
                getAllSubjectsForUserQuery =conn.prepareStatement("SELECT * from subject s where s.user=?");
            }catch (SQLException e){
                regenerisiBazu();
                try {
                    getAllSubjectsForUserQuery =conn.prepareStatement("SELECT * from subject s where s.user=?");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            try{
                getUserByUsernameQuery=conn.prepareStatement("SELECT * from user where username=?");
                addNewSubjectQuery=conn.prepareStatement("INSERT INTO subject VALUES (?,?,?,?)");
                removeSubjectQuery=conn.prepareStatement("DELETE FROM subject WHERE subject_name=?");
                editSubjectQuery=conn.prepareStatement("UPDATE subject SET subject_name=?, user=?, teacher=?, classroom=? where subject_name=?");
            }catch (SQLException e){
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner( new FileInputStream("timetable.db.sql"));
            String sqlUpit="";
            while(ulaz.hasNext()){
                sqlUpit+=ulaz.nextLine();
                if(sqlUpit.charAt(sqlUpit.length()-1)==';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit="";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Subject> getAllSubjects(String username) throws SQLException {
        ArrayList<Subject> subjects=new ArrayList<>();

        getAllSubjectsForUserQuery.setString(1,username);
        ResultSet rs = getAllSubjectsForUserQuery.executeQuery();
        while(rs.next()){
            Subject subject = new Subject(rs.getString(1),rs.getString(3),rs.getString(4),null);
            getUserByUsernameQuery.setString(1,rs.getString(2));
            ResultSet rs2 = getUserByUsernameQuery.executeQuery();
            if(rs2.next()){
                User user = new User(rs2.getString(2),rs2.getString(3),rs2.getString(4),rs2.getString(1),rs2.getString(5));
                subject.setUser(user);
            }
            subjects.add(subject);
        }
        return subjects;
    }

    public void addNewSubject(Subject subject) throws SQLException {
        addNewSubjectQuery.setString(1,subject.getSubjectName());
        addNewSubjectQuery.setString(2,subject.getUser().getUsername());
        addNewSubjectQuery.setString(3,subject.getTeacher());
        addNewSubjectQuery.setString(4,subject.getClassroom());
        addNewSubjectQuery.executeUpdate();
    }

    public User getUser(String username) throws SQLException {
        getUserByUsernameQuery.setString(1,username);
        User user = null;
        ResultSet rs = getUserByUsernameQuery.executeQuery();
        if(rs.next()){
            user = new User(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(1),rs.getString(5));
        }
        return user;
    }

    public void removeSubject(Subject subject) throws SQLException {
        removeSubjectQuery.setString(1,subject.getSubjectName());
        removeSubjectQuery.executeUpdate();
    }

    public void editSubject(Subject newSubject, Subject oldSubject) throws SQLException {
        editSubjectQuery.setString(1,newSubject.getSubjectName());
        editSubjectQuery.setString(2,newSubject.getUser().getUsername());
        editSubjectQuery.setString(3,newSubject.getTeacher());
        editSubjectQuery.setString(4,newSubject.getClassroom());
        editSubjectQuery.setString(5,oldSubject.getSubjectName());
        editSubjectQuery.executeUpdate();
    }
}
