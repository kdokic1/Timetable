package ba.unsa.etf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class TimetableDAO {
    private static TimetableDAO instance = null;
    private Connection conn;

    public Connection getConn() {
        return conn;
    }

    private PreparedStatement getAllTimetablesQuery;

    public static TimetableDAO getInstance() {
        if (instance == null) instance = new TimetableDAO(); //samo jedna instanca baze, da bi postojao samo jedan ulaz
        return instance;
    }

    private TimetableDAO(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:timetable.db"); //prvo sve konektujemo sa bazom

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

//            try{
//
//            }catch (SQLException e){
//                e.printStackTrace();
//            }
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


}
