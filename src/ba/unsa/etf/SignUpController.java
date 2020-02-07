package ba.unsa.etf;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {
    public ImageView imgView2=new ImageView();
    public Button btnSignUp = new Button();

    @FXML
    public void signIn(MouseEvent mouseEvent) throws IOException {
        Stage stg = (Stage) imgView2.getScene().getWindow();
        stg.close();

        Stage signupStage = new Stage();

        FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        LoginController ctrl = new LoginController();
        loader.setController(ctrl);
        Parent root = loader.load();
        signupStage.setTitle("Timetable");
        Image img = new Image("/images/mfp.png");
        imgView2.setImage(img);
        signupStage.setScene(new Scene(root, 700, 500));
        signupStage.show();
    }
}
