package ba.unsa.etf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UsersDAO {
    private static UsersDAO instance = null;
    private static Connection conn;

    public static Connection getConn() {
        return conn;
    }

    private PreparedStatement getAllUsers, addUser;

    public static UsersDAO getInstance() {
        if (instance == null) instance = new UsersDAO(); //samo jedna instanca baze, da bi postojao samo jedan ulaz
        return instance;
    }

    private UsersDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:timetable.db"); //prvo sve konektujemo sa bazom

        try{
            getAllUsers=conn.prepareStatement("SELECT * from user");
        }catch (SQLException e){
            regenerisiBazu();
            try {
                getAllUsers=conn.prepareStatement("SELECT * from user");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        try{
            addUser=conn.prepareStatement("INSERT INTO user(username,first_name,last_name, email, password) values (?,?,?,?,?)");
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

    public ArrayList<User> getAllUsers(){
        ArrayList<User> users=new ArrayList<>();

        try{
            ResultSet rs = getAllUsers.executeQuery();
            while(rs.next()){
                User user = new User(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(1),rs.getString(5));
                users.add(user);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return users;
    }

    public void addUser(User user){
        if(user!=null){
            try {
                addUser.setString(1,user.getUsername());
                addUser.setString(2,user.getFirstName());
                addUser.setString(3,user.getLastName());
                addUser.setString(4,user.getEmail());
                addUser.setString(5,user.getPassword());
                addUser.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
