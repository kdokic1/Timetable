package ba.unsa.etf;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginController extends Application {
    public ImageView imgView=new ImageView();
    public TextField loginUsername=new TextField();
    public PasswordField loginPass=new PasswordField();
    public Label errorLabel = new Label();
    public UsersDAO dao = UsersDAO.getInstance();

    @FXML
    public void initialize(){

    }

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


    public void signup(MouseEvent mouseEvent) throws IOException {
        Stage stg = (Stage) imgView.getScene().getWindow();
        stg.close();

        Stage signupStage = new Stage();

        FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/signup.fxml"));
        SignUpController ctrl = new SignUpController();
        loader.setController(ctrl);
        Parent root = loader.load();
        signupStage.setTitle("Timetable");
        Image img = new Image("/images/mfp.png");
        imgView.setImage(img);
        signupStage.setScene(new Scene(root, 700, 610));
        signupStage.show();
    }

    public void loginAction(ActionEvent actionEvent) throws IOException, SQLException {
        loginUsername.getStyleClass().remove("myborderregion");
        loginPass.getStyleClass().remove("myborderregion");
        if(!errorLabel.getText().isEmpty())
            errorLabel.setText("");
        ArrayList<User> users = new ArrayList<>();
        users=dao.getAllUsers();

        boolean userExists = users.stream().anyMatch(user -> user.getUsername().equals(loginUsername.getText()) && user.getPassword().equals(loginPass.getText()));

        if(userExists){
            String username=loginUsername.getText();
            Stage stg = (Stage) loginUsername.getScene().getWindow();
            stg.close();

            Stage startStage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/startPage.fxml"));
            StartPageController ctrl = new StartPageController(username);
            loader.setController(ctrl);
            Parent root = loader.load();
            startStage.setTitle("Timetable");
            ctrl.setUsername(username);
            ctrl.setUserLabelText(username);
            startStage.setScene(new Scene(root,700,460));
            startStage.show();
        }

        errorLabel.setText("*Invalid username or password");
        loginUsername.getStyleClass().add("myborderregion");
        loginPass.getStyleClass().add("myborderregion");
    }

}
