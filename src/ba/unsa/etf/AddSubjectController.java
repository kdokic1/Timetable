package ba.unsa.etf;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class AddSubjectController {
    private Subject subject=null;
    public TextField fldSubjectName=new TextField();
    public TextField fldTeacher=new TextField();
    public TextField fldClassroom=new TextField();
    private SubjectDAO dao = SubjectDAO.getInstance();
    private ArrayList<Subject> subjects = new ArrayList<>();
    private String username = new String();
    public Label errorLabel=new Label();

    public void setUsername(String username){
        this.username=username;
    }

    public void okAction(ActionEvent actionEvent) throws SQLException {
        subjects=dao.getAllSubjects(username);
        if(!errorLabel.getText().isEmpty())
            errorLabel.setText("");

        if(fldSubjectName.getText().isEmpty() || fldTeacher.getText().isEmpty() || fldClassroom.getText().isEmpty()){
            errorLabel.setText("Some field might be empty");
            return;
        }

        for(Subject s : subjects){
            if(s.getSubjectName().equals(fldSubjectName.getText())){
                errorLabel.setText("That subject already exists");
                return;
            }
        }

        subject=new Subject(fldSubjectName.getText(),fldTeacher.getText(),fldClassroom.getText(),null);

        Stage stg = (Stage) fldTeacher.getScene().getWindow();
        stg.close();
    }

    public void cancelAction(ActionEvent actionEvent){
        Stage stage = (Stage) fldClassroom.getScene().getWindow();
        stage.close();
    }

    public Subject getSubject(){
        return subject;
    }
}
