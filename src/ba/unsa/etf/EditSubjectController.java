package ba.unsa.etf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.sql.SQLException;
import java.util.ArrayList;

public class EditSubjectController {
    private String username = new String();
    public ChoiceBox<Subject> cbSubject= new ChoiceBox<>();
    public ArrayList<Subject> subjects=new ArrayList<>();
    public ObservableList<Subject> observableListSubjects = FXCollections.observableArrayList();
    public SubjectDAO dao = SubjectDAO.getInstance();
    public TextField fldName=new TextField();
    public TextField fldTeacher=new TextField();
    public TextField fldClassroom=new TextField();
    private Subject editedSubject=null;
    private Subject oldSubject=null;

    @FXML
    public void initialize() throws SQLException {
        subjects=dao.getAllSubjects(username);
        observableListSubjects.addAll(subjects);
        cbSubject.setItems(observableListSubjects);

        cbSubject.getSelectionModel().selectedItemProperty().addListener((obs, oldSubject, newSubject) ->{
            if(newSubject!=null){
                fldName.setText(newSubject.getSubjectName());
                fldTeacher.setText(newSubject.getTeacher());
                fldClassroom.setText(newSubject.getClassroom());
            }
        });
    }

    public EditSubjectController(String username){
        this.username=username;
    }

    public Subject getEditedSubject(){
        return editedSubject;
    }

    public Subject getOldSubject(){return oldSubject;}

    public void okAction(ActionEvent actionEvent) throws SQLException {
        if(cbSubject.getValue()!=null){
            oldSubject=cbSubject.getValue();
            editedSubject=new Subject(oldSubject.getSubjectName(), oldSubject.getTeacher(),oldSubject.getClassroom(),oldSubject.getUser());
            if(!fldName.getText().isEmpty()){
                boolean subjectNameExist=subjects.stream().filter(subject -> !oldSubject.getSubjectName().equals(subject.getSubjectName())).anyMatch(subject -> subject.getSubjectName().equals(fldName.getText()));
                if(subjectNameExist){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("That subject already exists");
                    alert.showAndWait();
                    return;
                }
                else {
                    editedSubject.setSubjectName(fldName.getText());
                }
            }

            if(!fldClassroom.getText().isEmpty()){
                editedSubject.setClassroom(fldClassroom.getText());
            }

            if(!fldTeacher.getText().isEmpty()){
                editedSubject.setTeacher(fldTeacher.getText());
            }
        }

        Stage stg = (Stage) fldName.getScene().getWindow();
        stg.close();
    }

    public void cancelAction(ActionEvent actionEvent){
        Stage stg = (Stage) fldName.getScene().getWindow();
        stg.close();
    }
}
