package ba.unsa.etf;

import javafx.event.ActionEvent;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;


public class AddTimetableController {
    private String username= new String();
    private Timetable timetable=null;
    public TextField timetableTitle = new TextField();
    public CheckBox cbSaturday = new CheckBox();
    private Timetables timetables;


    public AddTimetableController(String username, Timetables timetables){
        this.username=username;
        this.timetables=new Timetables(timetables);
    }

    public Timetable getTimetable(){
        return timetable;
    }

    private void closeStage(){
        Stage stage = (Stage) timetableTitle.getScene().getWindow();
        stage.close();
    }

    public void okAction(ActionEvent actionEvent) throws SQLException {
        if(!timetableTitle.getText().isEmpty()){
            for(Timetable t : timetables.getTimetables()){
                if(t.getTimetableName().equals(timetableTitle)){
                    closeStage();
                }
            }
            timetable=new Timetable(timetableTitle.getText(),null, cbSaturday.isSelected());
        }
        closeStage();
    }

    public void cancelAction(ActionEvent actionEvent){
        closeStage();
    }


}
