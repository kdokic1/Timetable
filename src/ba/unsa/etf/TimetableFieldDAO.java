package ba.unsa.etf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class TimetableFieldDAO {
    private static TimetableFieldDAO instance = null;
    private static Connection conn;
    private SubjectDAO subjectDAO=SubjectDAO.getInstance();

    public static Connection getConn() {
        return conn;
    }

    private PreparedStatement getTimetableFieldQuery;

    public static TimetableFieldDAO getInstance() {
        if (instance == null) instance = new TimetableFieldDAO(); //samo jedna instanca baze, da bi postojao samo jedan ulaz
        return instance;
    }

    private TimetableFieldDAO(){
        //conn = DriverManager.getConnection("jdbc:sqlite:timetable.db"); //prvo sve konektujemo sa bazom
        conn = UsersDAO.getConn();


        try{
            getTimetableFieldQuery =conn.prepareStatement("SELECT * from timetable_field t where t.user=?, t.timetable=?, t.ordinal_number=?,t.day=?");
        }catch (SQLException e){
            regenerisiBazu();
            try {
                getTimetableFieldQuery =conn.prepareStatement("SELECT * from timetable_field t where t.user=?, t.timetable=?, t.ordinal_number=?,t.day=?");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

//        try{
//
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
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

    public TimetableField getTimetableField(User user, Timetable timetable, int number, Day day) throws SQLException {
        TimetableField field = null;
        getTimetableFieldQuery.setString(1,user.getUsername());
        getTimetableFieldQuery.setString(2,timetable.getTimetableName());
        getTimetableFieldQuery.setInt(3,number);
        getTimetableFieldQuery.setString(4,getDayInString(day));
        ResultSet rs = getTimetableFieldQuery.executeQuery();
        if(rs.next()){
            TimeClass starts=new TimeClass(rs.getInt(6),rs.getInt(7));
            TimeClass ends = new TimeClass(rs.getInt(8),rs.getInt(9));
            field=new TimetableField(user,timetable,subjectDAO.getSubjectByUserAndName(user,rs.getString(5)),starts,ends,day,number);
        }
        return field;
    }

    private String getDayInString(Day day){
        if(day.getDay()==Day.MON)
            return "MON";
        else if(day.getDay()==Day.TUE)
            return "TUE";
        else if(day.getDay()==Day.WED)
            return "WED";
        else if(day.getDay()==Day.THU)
            return "THU";
        else if(day.getDay()==Day.FRI)
            return "FRI";
        else
            return "SAT";
    }
}
