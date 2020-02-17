package ba.unsa.etf;

import javafx.application.Application;
import javafx.concurrent.Task;
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
import java.util.Locale;
import java.util.ResourceBundle;

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
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        LoginController ctrl = new LoginController();
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/fxml/login.fxml"),bundle);
        loader.setController(ctrl);
        Parent root = loader.load();
        if(Locale.getDefault().getCountry().equals("BA"))
            primaryStage.setTitle("Raspored");
        else
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

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/signup.fxml"),bundle);
        SignUpController ctrl = new SignUpController();
        loader.setController(ctrl);
        Parent root = loader.load();
        if(Locale.getDefault().getCountry().equals("BA"))
            signupStage.setTitle("Raspored");
        else
            signupStage.setTitle("Timetable");
        Image img = new Image("/images/mfp.png");
        imgView.setImage(img);
        signupStage.setScene(new Scene(root, 700, 610));
        signupStage.show();
    }

    public void engAction(MouseEvent mouseEvent) throws IOException {
        Locale.setDefault(new Locale("en","US"));
        changeLanguage();
    }

    public void bosAction(MouseEvent mouseEvent) throws IOException {
        Locale.setDefault(new Locale("bs","BA"));
        changeLanguage();
    }


    private void changeLanguage() throws IOException {
        Stage stage = (Stage) loginPass.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"),ResourceBundle.getBundle("Translation"));
        loader.setController(this);
        stage.setScene(new Scene(loader.load()));
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
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            StartPageController ctrl = new StartPageController(username);
            final Parent[] roots={null};

            Task<Boolean> loadingTask =new Task<> () {
                @Override
                protected Boolean call() {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/startPage.fxml"), bundle);
                    loader.setController(ctrl);
                    try {
                        roots[0] = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            };

            loadingTask.setOnSucceeded(workerStateEvent ->{
                if(Locale.getDefault().getCountry().equals("BA"))
                    startStage.setTitle("Raspored");
                else
                    startStage.setTitle("Timetable");
                ctrl.setUsername(username);
                ctrl.setUserLabelText(username);
                startStage.setScene(new Scene(roots[0],700,460));
                startStage.show();
            });


            Parent secRoot = null;
            try{
                secRoot=FXMLLoader.load(getClass().getResource("/fxml/loading.fxml"),bundle);
                secRoot.setVisible(true);
            }catch(IOException e){
                e.printStackTrace();
            }

            if(Locale.getDefault().getCountry().equals("BA"))
                startStage.setTitle("Raspored");
            else
                startStage.setTitle("Timetable");
            ctrl.setUsername(username);
            ctrl.setUserLabelText(username);
            startStage.setScene(new Scene(secRoot,700,460));
            startStage.show();

            Thread thread = new Thread(loadingTask);
            thread.start();

        }

        if(Locale.getDefault().getCountry().equals("Ba"))
            errorLabel.setText("Pogresno korisnicko ime ili lozinka");
        else
            errorLabel.setText("*Invalid username or password");
        loginUsername.getStyleClass().add("myborderregion");
        loginPass.getStyleClass().add("myborderregion");
    }

//    public void loginAction(ActionEvent actionEvent) throws IOException, SQLException {
//        loginUsername.getStyleClass().remove("myborderregion");
//        loginPass.getStyleClass().remove("myborderregion");
//        if(!errorLabel.getText().isEmpty())
//            errorLabel.setText("");
//        ArrayList<User> users = new ArrayList<>();
//        users=dao.getAllUsers();
//
//        boolean userExists = users.stream().anyMatch(user -> user.getUsername().equals(loginUsername.getText()) && user.getPassword().equals(loginPass.getText()));
//
//        if(userExists){
//            String username=loginUsername.getText();
//            Stage stg = (Stage) loginUsername.getScene().getWindow();
//            stg.close();
//
//            Stage startStage=new Stage();
//            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
//
//            final Parent[] roots={null};
//
//            Task<Boolean> loadingTask=()->{
//
//            };
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/startPage.fxml"),bundle);
//            StartPageController ctrl = new StartPageController(username);
//            loader.setController(ctrl);
//            Parent root = loader.load();
//            if(Locale.getDefault().getCountry().equals("BA"))
//                startStage.setTitle("Raspored");
//            else
//                startStage.setTitle("Timetable");
//            ctrl.setUsername(username);
//            ctrl.setUserLabelText(username);
//            startStage.setScene(new Scene(root,700,460));
//            startStage.show();
//        }
//
//        if(Locale.getDefault().getCountry().equals("Ba"))
//            errorLabel.setText("Pogresno korisnicko ime ili lozinka");
//        else
//            errorLabel.setText("*Invalid username or password");
//        loginUsername.getStyleClass().add("myborderregion");
//        loginPass.getStyleClass().add("myborderregion");
//    }

}
