package ba.unsa.etf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class AddFieldInTimetableController {
    public Spinner<Integer> spinnerHours = new Spinner<>();
    public Spinner<Integer> spinnerMinutes = new Spinner<>();
    public ChoiceBox<Subject> cbSubjects = new ChoiceBox<>();
    private TimetableField timetableField=null;
    private Subjects subjects;
    private ObservableList<Subject> observableListSubjects = FXCollections.observableArrayList();
    private Day day;
    private int ordinalNumber;

    public TimetableField getTimetableField(){
        return timetableField;
    }

    public void initialize(){
        observableListSubjects.addAll(subjects.getSubjects());
        cbSubjects.setItems(observableListSubjects);
        SpinnerValueFactory<Integer> hours = new SpinnerValueFactory.IntegerSpinnerValueFactory(07,19,07);
        spinnerHours.setValueFactory(hours);
        SpinnerValueFactory<Integer> minutes = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,00);
        spinnerMinutes.setValueFactory(minutes);
    }

    public AddFieldInTimetableController(ArrayList<Subject> subjects, Day day, int number){
        this.subjects=new Subjects(subjects);
        this.day=day;
        this.ordinalNumber=number;
    }

    public void okAction(ActionEvent actionEvent){
        if(cbSubjects.getValue()!=null){
            TimeClass starts = new TimeClass(spinnerHours.getValue(),spinnerMinutes.getValue());
            TimeClass ends = new TimeClass(starts);
            ends.addMinutes(45);
            timetableField=new TimetableField(null,null,cbSubjects.getValue(),starts,ends,day,ordinalNumber);
        }
        close();
    }

    private void close(){
        Stage stage = (Stage) cbSubjects.getScene().getWindow();
        stage.close();
    }

    public void cancelAction(ActionEvent actionEvent){
        close();
    }
}
