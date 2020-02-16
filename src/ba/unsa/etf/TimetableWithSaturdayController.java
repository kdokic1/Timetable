package ba.unsa.etf;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class TimetableWithSaturdayController{
    protected String timetableName=new String();
    protected String username = new String();
    public Label timetableNameLabel=new Label();
    protected SubjectDAO subjectDAO=SubjectDAO.getInstance();
    protected TimetableDAO timetableDAO=TimetableDAO.getInstance();
    protected Timetable timetable;
    protected ArrayList<Button> buttons=new ArrayList<>();

    public Button MON1,MON2,MON3,MON4,MON5,MON6,MON7,TUE1,TUE2,TUE3,TUE4,TUE5,TUE6,TUE7,WED1,WED2,WED3,WED4,WED5,WED6,WED7,
            THU1,THU2,THU3,THU4,THU5,THU6,THU7,FRI1,FRI2,FRI3,FRI4,FRI5,FRI6,FRI7,SAT1,SAT2,SAT3,SAT4,SAT5,SAT6,SAT7;

    public TimetableWithSaturdayController(Timetable timetable, String username){
        this.timetable=timetable;
        this.username=username;
        this.timetableName=timetable.getTimetableName();
    }

    public void initialize() throws SQLException {
        timetableNameLabel.setText(timetableName.toUpperCase());
        ArrayList<TimetableField> timetableFields=timetableDAO.getFields(timetable);
        for(TimetableField t : timetableFields){
            if(t.getUser().getUsername().equals(username)) {
                Day day = t.getDay();
                String btnID = t.getDay().toString() + t.getOrdinalNumber();

                switch (btnID) {
                    case "MON1":
                        MON1.setText(t.getSubject().getSubjectName());
                        break;
                    case "MON2":
                        MON2.setText(t.getSubject().getSubjectName());
                        break;
                    case "MON3":
                        MON3.setText(t.getSubject().getSubjectName());
                        break;
                    case "MON4":
                        MON4.setText(t.getSubject().getSubjectName());
                        break;
                    case "MON5":
                        MON5.setText(t.getSubject().getSubjectName());
                        break;
                    case "MON6":
                        MON6.setText(t.getSubject().getSubjectName());
                        break;
                    case "MON7":
                        MON7.setText(t.getSubject().getSubjectName());
                        break;
                    case "TUE1":
                        TUE1.setText(t.getSubject().getSubjectName());
                        break;
                    case "TUE2":
                        TUE2.setText(t.getSubject().getSubjectName());
                        break;
                    case "TUE3":
                        TUE3.setText(t.getSubject().getSubjectName());
                        break;
                    case "TUE4":
                        TUE4.setText(t.getSubject().getSubjectName());
                        break;
                    case "TUE5":
                        TUE5.setText(t.getSubject().getSubjectName());
                        break;
                    case "TUE6":
                        TUE6.setText(t.getSubject().getSubjectName());
                        break;
                    case "TUE7":
                        TUE7.setText(t.getSubject().getSubjectName());
                        break;
                    case "WED1":
                        WED1.setText(t.getSubject().getSubjectName());
                        break;
                    case "WED2":
                        WED2.setText(t.getSubject().getSubjectName());
                        break;
                    case "WED3":
                        WED3.setText(t.getSubject().getSubjectName());
                        break;
                    case "WED4":
                        WED4.setText(t.getSubject().getSubjectName());
                        break;
                    case "WED5":
                        WED5.setText(t.getSubject().getSubjectName());
                        break;
                    case "WED6":
                        WED6.setText(t.getSubject().getSubjectName());
                        break;
                    case "WED7":
                        WED7.setText(t.getSubject().getSubjectName());
                        break;
                    case "THU1":
                        THU1.setText(t.getSubject().getSubjectName());
                        break;
                    case "THU2":
                        THU2.setText(t.getSubject().getSubjectName());
                        break;
                    case "THU3":
                        THU3.setText(t.getSubject().getSubjectName());
                        break;
                    case "THU4":
                        THU4.setText(t.getSubject().getSubjectName());
                        break;
                    case "THU5":
                        THU5.setText(t.getSubject().getSubjectName());
                        break;
                    case "THU6":
                        THU6.setText(t.getSubject().getSubjectName());
                        break;
                    case "THU7":
                        THU7.setText(t.getSubject().getSubjectName());
                        break;
                    case "FRI1":
                        FRI1.setText(t.getSubject().getSubjectName());
                        break;
                    case "FRI2":
                        FRI2.setText(t.getSubject().getSubjectName());
                        break;
                    case "FRI3":
                        FRI3.setText(t.getSubject().getSubjectName());
                        break;
                    case "FRI4":
                        FRI4.setText(t.getSubject().getSubjectName());
                        break;
                    case "FRI5":
                        FRI5.setText(t.getSubject().getSubjectName());
                        break;
                    case "FRI6":
                        FRI6.setText(t.getSubject().getSubjectName());
                        break;
                    case "FRI7":
                        FRI7.setText(t.getSubject().getSubjectName());
                        break;
                    case "SAT1":
                        SAT1.setText(t.getSubject().getSubjectName());
                        break;
                    case "SAT2":
                        SAT2.setText(t.getSubject().getSubjectName());
                        break;
                    case "SAT3":
                        SAT3.setText(t.getSubject().getSubjectName());
                        break;
                    case "SAT4":
                        SAT4.setText(t.getSubject().getSubjectName());
                        break;
                    case "SAT5":
                        SAT5.setText(t.getSubject().getSubjectName());
                        break;
                    case "SAT6":
                        SAT6.setText(t.getSubject().getSubjectName());
                        break;
                    case "SAT7":
                        SAT7.setText(t.getSubject().getSubjectName());
                        break;
                }
            }
        }
    }

    public void moreInfoAction(ActionEvent actionEvent) throws IOException, SQLException {
        Button btn = (Button) actionEvent.getSource();
        String id = btn.getId();
        String nameOfTheDay= id.substring(0,3);
        int ordinalNum = Integer.parseInt(id.substring(3));
        Day day=Day.MON;
        switch (nameOfTheDay){
            case "MON":
                day=Day.MON;
                break;
            case "TUE":
                day=Day.TUE;
                break;
            case "WED":
                day=Day.WED;
                break;
            case "THU":
                day=Day.THU;
                break;
            case "FRI":
                day=Day.FRI;
                break;
            case "SAT":
                day=Day.SAT;
        }
        if(btn.getText().equals("")){
            Stage addFieldStage = new Stage();
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addFieldInTimetable.fxml"),bundle);
            AddFieldInTimetableController ctrl = new AddFieldInTimetableController(subjectDAO.getAllSubjects(username),day,ordinalNum);
            loader.setController(ctrl);
            Parent root = loader.load();
            if(Locale.getDefault().getCountry().equals("BA"))
                addFieldStage.setTitle("Dodaj polje");
            else
                addFieldStage.setTitle("Add Field");
            addFieldStage.setScene(new Scene(root,320,180));
            addFieldStage.setOnHiding(event -> {
                if(ctrl.getTimetableField()!=null){
                    btn.setText(ctrl.getTimetableField().getSubject().getSubjectName());
                    TimetableField timetableField=ctrl.getTimetableField();
                    try {
                        User user=subjectDAO.getUser(username);
                        timetableField.setUser(user);
                        timetableField.setTimetable(timetable);

                        timetableDAO.addField(timetableField);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            addFieldStage.show();
        }
        else{
            Stage moreInfoStage = new Stage();
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/moreInformation.fxml"),bundle);
            MoreInformationController ctrl = new MoreInformationController(timetable,nameOfTheDay,ordinalNum,username);
            loader.setController(ctrl);
            Parent root = loader.load();
            if(Locale.getDefault().getCountry().equals("BA"))
                moreInfoStage.setTitle("Vise informacija");
            else
                moreInfoStage.setTitle("More infromation");
            moreInfoStage.setScene(new Scene(root,420,310));
            moreInfoStage.setOnHiding(event -> {
                if(ctrl.isDeleted()){
                    btn.setText("");
                }
            });
            moreInfoStage.show();
        }
    }

    public void backAction(MouseEvent mouseEvent) throws SQLException, IOException {
        Stage stage = (Stage) timetableNameLabel.getScene().getWindow();
        stage.close();

        Stage startStage=new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/startPage.fxml"),bundle);
        StartPageController ctrl = new StartPageController(username);
        loader.setController(ctrl);
        Parent root = loader.load();
        if(Locale.getDefault().getCountry().equals("BA"))
            startStage.setTitle("Raspored");
        else
            startStage.setTitle("Timetable");
        ctrl.setUsername(username);
        ctrl.setUserLabelText(username);
        startStage.setScene(new Scene(root,700,460));
        startStage.show();
    }
}
