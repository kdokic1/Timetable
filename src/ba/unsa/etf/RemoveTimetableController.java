package ba.unsa.etf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class RemoveTimetableController {
    private String username = new String();
    private Timetables timetables = new Timetables();
    public ChoiceBox<Timetable> cbTimetables = new ChoiceBox<>();
    private ObservableList<Timetable> allTimetables = FXCollections.observableArrayList();
    private Timetable timetableForRemove = null;


    public RemoveTimetableController(String username, Timetables timetables){
        this.username=username;
        this.timetables=timetables;
    }

    public Timetable getTimetableForRemove(){return timetableForRemove;}

    public void initialize(){
        allTimetables.addAll(timetables.getTimetables());
        cbTimetables.setItems(allTimetables);
    }

    private void closeStage(){
        Stage stage = (Stage) cbTimetables.getScene().getWindow();
        stage.close();
    }

    public void cancelAction(ActionEvent actionEvent){
        closeStage();
    }

    public void okAction(ActionEvent actionEvent){
        if(cbTimetables.getValue()!=null){
            timetableForRemove=cbTimetables.getValue();
        }
        closeStage();
    }
}
