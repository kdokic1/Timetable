package ba.unsa.etf;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.ArrayList;

public class MoreInformationController {
    private Timetable timetable;
    private TimetableDAO timetableDAO=TimetableDAO.getInstance();
    private ArrayList<TimetableField> fields= new ArrayList<>();
    public TextField subjectName= new TextField();
    public TextField teacherFld=new TextField();
    public TextField classroomFld=new TextField();
    public Label startLabel=new Label();
    public Label endLabel = new Label();
    private String day;
    private int ordinalNum;
    private String username;
    private TimetableField tempField;


    public MoreInformationController(Timetable timetable, String day, int ordinalNum, String username) throws SQLException {
        this.timetable=timetable;
        fields=timetableDAO.getFields(timetable);
        this.day=day;
        this.ordinalNum=ordinalNum;
        this.username=username;
    }

    public void initialize(){
        for(TimetableField tf : fields){
            if(tf.getUser().getUsername().equals(username) && tf.getOrdinalNumber()==ordinalNum && tf.getDay().toString().equals(day)){
                tempField=tf;
                subjectName.setText(tf.getSubject().getSubjectName());
                teacherFld.setText(tf.getSubject().getTeacher());
                classroomFld.setText(tf.getSubject().getClassroom());

                String start="";
                if(tf.getStarts().getHours()<10)
                    start+='0';
                int h = tf.getStarts().getHours();
                start+=String.valueOf(tf.getStarts().getHours())+':';
                if(tf.getStarts().getMinutes()<10)
                    start+='0';
                start+=String.valueOf(tf.getStarts().getMinutes());
                startLabel.setText(start);

                String ends="";
                if(tf.getEnds().getHours()<10)
                    ends+='0';
                ends+=String.valueOf(tf.getEnds().getHours())+':';
                if(tf.getEnds().getMinutes()<10)
                    ends+='0';
                ends+=String.valueOf(tf.getEnds().getMinutes());
                endLabel.setText(ends);
            }
        }

    }

    public void removeFieldAction(ActionEvent actionEvent){

    }

    public void okAction(ActionEvent actionEvent){

    }
}
