package ba.unsa.etf;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class SignUpController {
    public ImageView imgView2=new ImageView();
    public Button btnSignUp = new Button();
    public TextField fldFirstName=new TextField();
    public TextField fldLastName=new TextField();
    public TextField fldEmail=new TextField();
    public TextField fldUsername=new TextField();
    public PasswordField fldPass=new PasswordField();
    public Label errorLabel = new Label();
    public UsersDAO dao = UsersDAO.getInstance();

    @FXML
    public void initialize(){

    }

    public void signIn(MouseEvent mouseEvent) throws IOException {
        signInFun();
    }

    public void signInFun() throws IOException {
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

    public void signUpAction(ActionEvent actionEvent) throws IOException {
        ArrayList<User> users = new ArrayList<>();
        users=dao.getAllUsers();
        if(users!=null) {
            for (User u : users) {
                if (u.getUsername().equals(fldUsername.getText())) {
                    errorLabel.setText("This username is already used, try again");
                    return;
                }
            }
        }

        if(!validEmail(fldEmail.getText())){
            errorLabel.setText("Incorrect e-mail");
            return;
        }

        if(!validPass(fldPass.getText())){
            errorLabel.setText("Password should contains at least 8 characters, 1 number and 1 capital letter");
            return;
        }

        User user = new User(fldFirstName.getText(),fldLastName.getText(),fldEmail.getText(),fldUsername.getText(),fldPass.getText());

        dao.addUser(user);
        signInFun();
    }

    public boolean validEmail(String emailToCheck){
        if(!emailToCheck.contains("@"))
            return false;
        else{
            if(emailToCheck.indexOf('@')==emailToCheck.length()-1 || emailToCheck.indexOf('@')==0)
                return false;
        }

        return true;
    }

    public boolean validPass(String password){
        if(password.length()<8)
            return false;
        boolean containsNumber=false,containsCapitalLetter=false;

        for(int i=0; i<password.length(); i++){
            if(isCapitalLetter(password.charAt(i)))
                containsCapitalLetter=true;
            if(isNumber(password.charAt(i)))
                containsNumber=true;
        }

        return containsCapitalLetter && containsNumber;
    }

    public boolean isNumber(char c){
        return (c>='0' && c<='9');
    }

    public boolean isCapitalLetter(char l){
        return l>='A' && l<='Z';
    }
}
