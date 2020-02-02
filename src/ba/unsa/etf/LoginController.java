package ba.unsa.etf;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController extends Application {
    public ImageView imgView=new ImageView();

    @Override
    public void start(Stage primaryStage) throws Exception{
        LoginController ctrl = new LoginController();
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("Timetable");
        Image img = new Image("/images/mfp.png");
        imgView.setImage(img);
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }


    @FXML
    public void signup(ActionEvent actionEvent) throws IOException {
//        Stage stg = (Stage) imgView.getScene().getWindow();
//        stg.close();

//        Stage signupStage = new Stage();
//
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/signup.fxml"));
//        signupStage.setTitle("Timetable");
//        Image img = new Image("/images/mfp.png");
//        imgView.setImage(img);
//        signupStage.setScene(new Scene(root, 700, 500));
//        signupStage.show();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("haha");
        alert.setHeaderText("haha");
        alert.showAndWait();
    }

}
