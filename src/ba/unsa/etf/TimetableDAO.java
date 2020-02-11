package ba.unsa.etf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TimetableDAO {
    private static TimetableDAO instance = null;
    private static Connection conn;
    private SubjectDAO subjectDAO=SubjectDAO.getInstance();

    public static Connection getConn() {
        return conn;
    }

    private PreparedStatement getAllTimetablesQuery, getUserByUsername, addTimetableQuery, deleteTimetableQuery, getFieldsQuery,addFieldQuery,removeFieldQuery, editFieldQuery, deleteFieldsQuery, deleteFieldsAfterSubjectQuery;

    public static TimetableDAO getInstance() {
        if (instance == null) instance = new TimetableDAO(); //samo jedna instanca baze, da bi postojao samo jedan ulaz
        return instance;
    }

    private TimetableDAO(){
        //conn = DriverManager.getConnection("jdbc:sqlite:timetable.db"); //prvo sve konektujemo sa bazom
        conn = UsersDAO.getConn();


        try{
            getAllTimetablesQuery =conn.prepareStatement("SELECT * from timetable t where t.user=?");
        }catch (SQLException e){
            regenerisiBazu();
            try {
                getAllTimetablesQuery =conn.prepareStatement("SELECT * from timetable t where t.user=?");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        try{
            getUserByUsername =conn.prepareStatement("SELECT * from user where username=?");
            addTimetableQuery=conn.prepareStatement("INSERT INTO timetable values (?,?,?)");
            deleteTimetableQuery=conn.prepareStatement("DELETE from timetable where timetable_name=?");
            getFieldsQuery=conn.prepareStatement("SELECT * from timetable_field t where t.timetable=?");
            addFieldQuery=conn.prepareStatement("INSERT INTO timetable_field values(?,?,?,?,?,?,?,?,?)");
            removeFieldQuery=conn.prepareStatement("DELETE from timetable_field where username=? and timetable=? and ordinal_number=? and timetable_day=?");
            editFieldQuery=conn.prepareStatement("UPDATE timetable_field SET subject=? where username=? and subject=?");
            deleteFieldsQuery=conn.prepareStatement("DELETE from timetable_field where timetable=? and username=?");
            deleteFieldsAfterSubjectQuery=conn.prepareStatement("DELETE from timetable_field where subject=? and username=?");
        }catch (SQLException e){
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

    public Timetables getAllTimetablesForUser(String username) throws SQLException {
        getAllTimetablesQuery.setString(1,username);
        ResultSet rs = getAllTimetablesQuery.executeQuery();
        Timetables timetables=new Timetables();
        while(rs.next()){
            Timetable timetable = new Timetable(rs.getString(1),null,rs.getBoolean(3));
            User user = getUser(rs.getString(2));
            timetable.setUser(user);
            timetables.getTimetables().add(timetable);
        }
        return timetables;
    }

    public User getUser(String username) throws SQLException {
        getUserByUsername.setString(1,username);
        User user = null;
        ResultSet rs = getUserByUsername.executeQuery();
        if(rs.next()){
            user = new User(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(1),rs.getString(5));
        }
        return user;
    }

    public void addTimetable(Timetable timetable) throws SQLException {
        addTimetableQuery.setString(1,timetable.getTimetableName());
        addTimetableQuery.setString(2,timetable.getUser().getUsername());
        addTimetableQuery.setBoolean(3, timetable.isIncludeSaturday());
        addTimetableQuery.executeUpdate();
    }

    public void deleteTimetable(Timetable timetable) throws SQLException {
        deleteTimetableQuery.setString(1,timetable.getTimetableName());
        deleteTimetableQuery.executeUpdate();
    }
    public ArrayList<TimetableField> getFields(Timetable timetable) throws SQLException {
        ArrayList<TimetableField> timetableFields=new ArrayList<>();
        getFieldsQuery.setString(1,timetable.getTimetableName());

        ResultSet rs = getFieldsQuery.executeQuery();
       //(User user, Timetable timetable, Subject subject, TimeClass starts,TimeClass ends, Day day, int ordinalNumber) {
        while(rs.next()){
            TimetableField timetableField=new TimetableField();
            ArrayList<Subject> subjects=new ArrayList<>();
            subjects=subjectDAO.getAllSubjects(rs.getString(2));
            User user = subjectDAO.getUser(rs.getString(2));
            Subject newSubject=new Subject();

            for(Subject s: subjects){
                if(s.getSubjectName().equals(rs.getString(5))){
                    newSubject=s;
                    break;
                }
            }
            TimeClass starts = new TimeClass(rs.getInt(6),rs.getInt(7));
            TimeClass ends = new TimeClass(rs.getInt(8),rs.getInt(9));
            Day day = getDayByString(rs.getString(3));

            timetableField = new TimetableField(user,timetable,newSubject,starts,ends,day,rs.getInt(4));

            timetableFields.add(timetableField);

        }
        return timetableFields;
    }

    public void addField(TimetableField timetableField) throws SQLException {
        addFieldQuery.setString(1,timetableField.getTimetable().getTimetableName());
        addFieldQuery.setString(2,timetableField.getUser().getUsername());
        addFieldQuery.setString(3,timetableField.getDay().toString());
        addFieldQuery.setInt(4,timetableField.getOrdinalNumber());
        addFieldQuery.setString(5,timetableField.getSubject().getSubjectName());
        addFieldQuery.setInt(6,timetableField.getStarts().getHours());
        addFieldQuery.setInt(7,timetableField.getStarts().getMinutes());
        addFieldQuery.setInt(8,timetableField.getEnds().getHours());
        addFieldQuery.setInt(9,timetableField.getEnds().getMinutes());
        addFieldQuery.executeUpdate();
    }

    public void removeField(TimetableField timetableField) throws SQLException {
        removeFieldQuery.setString(1,timetableField.getUser().getUsername());
        removeFieldQuery.setString(2,timetableField.getTimetable().getTimetableName());
        removeFieldQuery.setInt(3,timetableField.getOrdinalNumber());
        removeFieldQuery.setString(4,timetableField.getDay().toString());
        removeFieldQuery.executeUpdate();
    }

    private Day getDayByString(String day){
        switch (day) {
            case "MON":
                return Day.MON;
            case "TUE":
                return Day.TUE;
            case "WED":
                return Day.WED;
            case "THU":
                return Day.THU;
            case "FRI":
                return Day.FRI;
            default:
                return Day.SAT;
        }
    }

    public void editField(Subject oldSubject, Subject newSubject) throws SQLException {
        editFieldQuery.setString(1,newSubject.getSubjectName());
        editFieldQuery.setString(2,newSubject.getUser().getUsername());
        editFieldQuery.setString(3,oldSubject.getSubjectName());
        editFieldQuery.executeUpdate();
    }

    public void deleteFieldAfterTimetable(String timetable, String username) throws SQLException {
        deleteFieldsQuery.setString(1,timetable);
        deleteFieldsQuery.setString(2,username);
        deleteFieldsQuery.executeUpdate();
    }

    public void deleteFieldAfterSubject(String subject, String username) throws SQLException {
        deleteFieldsAfterSubjectQuery.setString(1,subject);
        deleteFieldsAfterSubjectQuery.setString(2,username);
        deleteFieldsAfterSubjectQuery.executeUpdate();
    }

}
