package ba.unsa.etf;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class TimetableController {
    protected String timetableName=new String();
    protected String username = new String();
    private SubjectDAO subjectDAO=SubjectDAO.getInstance();

    public TimetableController(String timetableName, String username){
        this.timetableName=timetableName;
        this.username=username;
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
                day.setDay(Day.MON);
                break;
            case "TUE":
                day=Day.TUE;
                day.setDay(Day.TUE);
                break;
            case "WED":
                day=Day.WED;
                day.setDay(Day.WED);
                break;
            case "THU":
                day=Day.THU;
                day.setDay(Day.THU);
                break;
            case "FRI":
                day=Day.FRI;
                day.setDay(Day.FRI);
                break;
        }
        if(btn.getText().equals("")){
            Stage addFieldStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addFieldInTimetable.fxml"));
            AddFieldInTimetableController ctrl = new AddFieldInTimetableController(subjectDAO.getAllSubjects(username),day,ordinalNum);
            loader.setController(ctrl);
            Parent root = loader.load();
            addFieldStage.setTitle("Add Field");
            addFieldStage.setScene(new Scene(root,320,180));
            addFieldStage.setOnHiding(event -> {
                if(ctrl.getTimetableField()!=null){
                    btn.setText(ctrl.getTimetableField().getSubject().getSubjectName());
                }
            });
            addFieldStage.show();
        }
    }
}
