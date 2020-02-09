package ba.unsa.etf;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class StartPageController {
    public SubjectDAO dao = SubjectDAO.getInstance();
    public TimetableDAO timetableDAO = TimetableDAO.getInstance();
    public Label userLabel = new Label();
    public String username = new String();
    public ChoiceBox<Timetable> cbSubjects = new ChoiceBox<>();
    public ArrayList<Subject> subjects = new ArrayList<>();
    public ObservableList<Subject> subjectNames = FXCollections.observableArrayList();
    public ObservableList<Timetable> allTimetables= FXCollections.observableArrayList();
    private Timetables timetables=new Timetables();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserLabelText(String username){
        userLabel.setText(username);
    }


    @FXML
    public void initialize() throws SQLException {
//        subjects=dao.getAllSubjects(username);
//        subjectNames.addAll(subjects);
//        cbSubjects.setItems(subjectNames);

        timetables=timetableDAO.getAllTimetablesForUser(username);
        allTimetables.addAll(timetables.getTimetables());
        cbSubjects.setItems(allTimetables);


    }

    private void setItemsInCheckBox() throws SQLException {
        timetables=null;
        timetables=timetableDAO.getAllTimetablesForUser(username);
        allTimetables.addAll(timetables.getTimetables());
        cbSubjects.setItems(allTimetables);
    }

    public StartPageController(String username) throws SQLException {
        this.username=username;
        timetables=timetableDAO.getAllTimetablesForUser(username);
        subjects=dao.getAllSubjects(username);
    }


    public void addNewSubjectAction(ActionEvent actionEvent) throws IOException {
        Stage addSubjectStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addSubject.fxml"));
        AddSubjectController ctrl = new AddSubjectController();
        loader.setController(ctrl);
        Parent root = loader.load();
        addSubjectStage.setTitle("Add subject");
        addSubjectStage.setScene(new Scene(root,390,220));
        ctrl.setUsername(username);

        addSubjectStage.setOnHiding(event -> {
            if(ctrl.getSubject()!=null){
                Subject subject=ctrl.getSubject();
                try {
                    User user = dao.getUser(username);
                    subject.setUser(user);
                    dao.addNewSubject(subject);
                    setItemsInCheckBox();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        addSubjectStage.show();
    }

    public void removeSubjectAction(ActionEvent actionEvent) throws IOException {
        Stage removeSubjectStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/removeSubject.fxml"));
        RemoveSubjectController ctrl = new RemoveSubjectController(username);
        loader.setController(ctrl);
        Parent root = loader.load();
        removeSubjectStage.setTitle("Add subject");
        removeSubjectStage.setScene(new Scene(root,390,220));

        removeSubjectStage.setOnHiding(event -> {
            if(ctrl.getSubject()!=null){
                try {
                    dao.removeSubject(ctrl.getSubject());
                    setItemsInCheckBox();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        });

        removeSubjectStage.show();
    }

    public void editSubjectAction(ActionEvent actionEvent) throws IOException {
        Stage editSubjecStage= new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editSubject.fxml"));
        EditSubjectController ctrl = new EditSubjectController(username);
        loader.setController(ctrl);
        Parent root = loader.load();
        editSubjecStage.setTitle("Edit subject");
        editSubjecStage.setScene(new Scene(root,420,300));
        editSubjecStage.setOnHiding(event -> {
            if(ctrl.getEditedSubject()!=null){
                Subject newSubject = ctrl.getEditedSubject();
                Subject oldSubject = ctrl.getOldSubject();
                try {
                    dao.editSubject(newSubject,oldSubject);
                    setItemsInCheckBox();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        editSubjecStage.show();
    }
}
