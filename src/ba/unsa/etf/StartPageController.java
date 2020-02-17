package ba.unsa.etf;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class StartPageController {
    public SubjectDAO dao = SubjectDAO.getInstance();
    public TimetableDAO timetableDAO = TimetableDAO.getInstance();
    public Label userLabel = new Label();
    public String username = new String();
    public ChoiceBox<Timetable> cbTimetables = new ChoiceBox<>();
    public ArrayList<Subject> subjects = new ArrayList<>();
    public ObservableList<Subject> subjectNames = FXCollections.observableArrayList();
    public ObservableList<Timetable> allTimetables= FXCollections.observableArrayList();
    private Timetables timetables;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserLabelText(String username){
        if(Locale.getDefault().getCountry().equals("BA"))
            userLabel.setText("Zdravo "+username);
        else
            userLabel.setText("Hi "+username);
    }


    @FXML
    public void initialize() throws SQLException {
//        subjects=dao.getAllSubjects(username);
//        subjectNames.addAll(subjects);
//        cbSubjects.setItems(subjectNames);

        timetables= new Timetables(timetableDAO.getAllTimetablesForUser(username));
        allTimetables.removeAll(allTimetables);
        allTimetables.addAll(timetables.getTimetables());
        cbTimetables.setItems(allTimetables);



        if(Locale.getDefault().getCountry().equals("BA"))
            userLabel.setText("Zdravo "+username);
        else
            userLabel.setText("Hi "+username);


    }

    private void closeStage(){
        Stage stage = (Stage) cbTimetables.getScene().getWindow();
        stage.close();
    }

    private void setItemsInChoiceBox() throws SQLException {
        timetables.getTimetables().removeAll(timetables.getTimetables());
        timetables= new Timetables(timetableDAO.getAllTimetablesForUser(username));
        allTimetables.removeAll(allTimetables);
        allTimetables.addAll(timetables.getTimetables());
        cbTimetables.setItems(allTimetables);
    }

    public StartPageController(String username) throws SQLException {
        this.username=username;
        timetables=new Timetables(timetableDAO.getAllTimetablesForUser(username));
        subjects=dao.getAllSubjects(username);
    }


    public void addNewSubjectAction(ActionEvent actionEvent) throws IOException {
        Stage addSubjectStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addSubject.fxml"),bundle);
        AddSubjectController ctrl = new AddSubjectController();
        loader.setController(ctrl);
        Parent root = loader.load();
        if(Locale.getDefault().getCountry().equals("BA"))
            addSubjectStage.setTitle("Dodaj predmet");
        else
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
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        addSubjectStage.show();
    }

    public void removeSubjectAction(ActionEvent actionEvent) throws IOException {
        Stage removeSubjectStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/removeSubject.fxml"),bundle);
        RemoveSubjectController ctrl = new RemoveSubjectController(username);
        loader.setController(ctrl);
        Parent root = loader.load();
        if(Locale.getDefault().getCountry().equals("BA"))
            removeSubjectStage.setTitle("Dodaj predmet");
        else
            removeSubjectStage.setTitle("Add subject");
        removeSubjectStage.setScene(new Scene(root,390,220));

        removeSubjectStage.setOnHiding(event -> {
            if(ctrl.getSubject()!=null){
                try {
                    dao.removeSubject(ctrl.getSubject());
                    timetableDAO.deleteFieldAfterSubject(ctrl.getSubject().getSubjectName(),username);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        });

        removeSubjectStage.show();
    }

    public void editSubjectAction(ActionEvent actionEvent) throws IOException {
        Stage editSubjecStage= new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editSubject.fxml"),bundle);
        EditSubjectController ctrl = new EditSubjectController(username);
        loader.setController(ctrl);
        Parent root = loader.load();
        if(Locale.getDefault().getCountry().equals("BA"))
            editSubjecStage.setTitle("Uredi predmet");
        else
            editSubjecStage.setTitle("Edit subject");
        editSubjecStage.setScene(new Scene(root,420,300));
        editSubjecStage.setOnHiding(event -> {
            if(ctrl.getEditedSubject()!=null){
                Subject newSubject = ctrl.getEditedSubject();
                Subject oldSubject = ctrl.getOldSubject();
                try {
                    dao.editSubject(newSubject,oldSubject);
                    timetableDAO.editField(oldSubject,newSubject);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        editSubjecStage.show();
    }

    public void addNewTimetableAction(ActionEvent actionEvent) throws IOException {
        Stage addNewTimetableStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addTimetable.fxml"),bundle);
        AddTimetableController ctrl = new AddTimetableController(username,timetables);
        loader.setController(ctrl);
        Parent root = loader.load();
        if(Locale.getDefault().getCountry().equals("BA"))
            addNewTimetableStage.setTitle("Dodaj raspored");
        else
            addNewTimetableStage.setTitle("Add Timetable");
        addNewTimetableStage.setScene(new Scene(root,390,140));
        addNewTimetableStage.setOnHiding(event -> {
            if(ctrl.getTimetable()!=null){
                try {
                    User user = dao.getUser(username);
                    ctrl.getTimetable().setUser(user);
                    timetableDAO.addTimetable(ctrl.getTimetable());
                    setItemsInChoiceBox();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        addNewTimetableStage.show();
    }

    public void removeTimetableAction(ActionEvent actionEvent) throws IOException {
        Stage removeTimetableStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/removeTimetable.fxml"),bundle);
        RemoveTimetableController ctrl = new RemoveTimetableController(username,timetables);
        loader.setController(ctrl);
        Parent root = loader.load();
        if(Locale.getDefault().getCountry().equals("BA"))
            removeTimetableStage.setTitle("Obrisi raspored");
        else
            removeTimetableStage.setTitle("Remove Timetable");
        removeTimetableStage.setScene(new Scene(root,390,180));
        removeTimetableStage.setOnHiding(event -> {
            if(ctrl.getTimetableForRemove()!=null){
                try {
                    timetableDAO.deleteTimetable(ctrl.getTimetableForRemove());
                    timetableDAO.deleteFieldAfterTimetable(ctrl.getTimetableForRemove().getTimetableName(),username);
                    setItemsInChoiceBox();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        removeTimetableStage.show();
    }

    public void openAction(ActionEvent actionEvent) throws IOException {

        Stage timetableStage = new Stage();
        FXMLLoader loader;
        if (cbTimetables.getValue() != null) {
            closeStage();
            if (cbTimetables.getValue().isIncludeSaturday()) {
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                loader = new FXMLLoader(getClass().getResource("/fxml/timetableWithSaturday.fxml"),bundle);
                TimetableWithSaturdayController ctrl = new TimetableWithSaturdayController(cbTimetables.getValue(),username);
                loader.setController(ctrl);
                Parent root = loader.load();
                if(Locale.getDefault().getCountry().equals("BA"))
                    timetableStage.setTitle("Raspored");
                else
                    timetableStage.setTitle("Timetable");
                timetableStage.setScene(new Scene(root,1130,700));
            } else {
                ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                loader = new FXMLLoader(getClass().getResource("/fxml/timetable.fxml"),bundle);
                TimetableController ctrl = new TimetableController(cbTimetables.getValue(),username);
                loader.setController(ctrl);
                Parent root = loader.load();
                if(Locale.getDefault().getCountry().equals("BA"))
                    timetableStage.setTitle("Raspored");
                else
                    timetableStage.setTitle("Timetable");
                timetableStage.setScene(new Scene(root,965,700));
            }

            timetableStage.show();

        }
    }

    public void logoutAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) userLabel.getScene().getWindow();
        stage.close();

        ResourceBundle resourceBundle=ResourceBundle.getBundle("Translation");
        LoginController ctrl = new LoginController();
        Stage primaryStage = new Stage();
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/fxml/login.fxml"),resourceBundle);
        loader.setController(ctrl);
        Parent root = loader.load();
        if(Locale.getDefault().getCountry().equals("BA"))
            primaryStage.setTitle("Raspored");
        else
            primaryStage.setTitle("Timetable");
        Image img = new Image("/images/mfp.png");
        ctrl.imgView.setImage(img);
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();

    }

    public void closeAction(ActionEvent actionEvent){
        System.exit(0);
    }

    public void aboutAction(ActionEvent actionEvent){
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        if(Locale.getDefault().getCountry().equals("BA")) {
            alert.setTitle("Informacije o aplikaciji");
            alert.setHeaderText(null);
            alert.setContentText("MFP Timetable je softver koji služi za kreiranje različitih rasporeda.\n" +
                    "MFP u nazivu softvera znači 'My first project'.\n" +
                    "Ovaj projekat je kreiran od strane Kanite Đokić kao fakultetski projekat.\n" +
                    "Trenutna verzija: 1.0.1");
        }
        else{
            alert.setHeaderText(null);
            alert.setTitle("App information");
            alert.setContentText("MFP Timetable is software for creating different timetables.\n" +
                    "MPF in software title means 'My first project'.\n" +
                    "This project is created by Kanita Đokić.\n" +
                    "Current version: 1.0.1");
        }
        alert.showAndWait();
    }
    public void bosnianLanguage(ActionEvent actionEvent) throws IOException {
        Locale.setDefault(new Locale("bs","BA"));
        changeLanguage();
    }

    public void englishLanguage(ActionEvent actionEvent) throws IOException {
        Locale.setDefault(new Locale("en","US"));
        changeLanguage();
    }

    private void changeLanguage() throws IOException {
        if(Locale.getDefault().getCountry().equals("BA"))
            userLabel.setText("Zdravo "+username);
        else
            userLabel.setText("Hi "+username);
        Stage stage = (Stage) userLabel.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/startPage.fxml"),ResourceBundle.getBundle("Translation"));
        loader.setController(this);
        stage.setScene(new Scene(loader.load()));
    }

    public void printAction(ActionEvent actionEvent){
        try {
            new PrintReport().showReport(UsersDAO.getConn());
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

}
