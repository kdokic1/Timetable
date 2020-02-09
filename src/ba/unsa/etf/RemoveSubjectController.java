package ba.unsa.etf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class RemoveSubjectController {
    public ChoiceBox<Subject> cbSubjects=new ChoiceBox<>();
    public ArrayList<Subject> subjects = new ArrayList<>();
    public ObservableList<Subject> observableListSubjects= FXCollections.observableArrayList();
    private String username=new String();
    private Subject subject = null;
    public SubjectDAO dao = SubjectDAO.getInstance();

    public RemoveSubjectController(String username){
        this.username=username;
    }

    @FXML
    public void initialize() throws SQLException {
        subjects=dao.getAllSubjects(username);
        observableListSubjects.addAll(subjects);
        cbSubjects.setItems(observableListSubjects);
    }


    public void okAction(ActionEvent actionEvent){
        if(cbSubjects.getValue()!=null){
            subject=cbSubjects.getValue();
        }

        Stage stg = (Stage) cbSubjects.getScene().getWindow();
        stg.close();
    }

    public void cancelAction(ActionEvent actionEvent){
        Stage stg = (Stage) cbSubjects.getScene().getWindow();
        stg.close();
    }

    public Subject getSubject(){
        return subject;
    }
}
