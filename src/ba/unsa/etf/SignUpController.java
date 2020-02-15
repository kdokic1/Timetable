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
        fldUsername.getStyleClass().remove("myborderregion");
        fldLastName.getStyleClass().remove("myborderregion");
        fldFirstName.getStyleClass().remove("myborderregion");
        fldEmail.getStyleClass().remove("myborderregion");
        fldPass.getStyleClass().remove("myborderregion");

        if(!errorLabel.getText().isEmpty())
            errorLabel.setText("");

        ArrayList<User> users = new ArrayList<>();
        users=dao.getAllUsers();

        String errorText = "";

        if(fldUsername.getText().isEmpty() || fldFirstName.getText().isEmpty() || fldLastName.getText().isEmpty() || fldEmail.getText().isEmpty() || fldPass.getText().isEmpty()){
            errorText=errorText+"*You did not enter all fields\n";
        }

        if(fldUsername.getText().isEmpty()){
            fldUsername.getStyleClass().add("myborderregion");
        }

        if(fldFirstName.getText().isEmpty()){
            fldFirstName.getStyleClass().add("myborderregion");
        }

        if(fldLastName.getText().isEmpty()){
            fldLastName.getStyleClass().add("myborderregion");
        }

        if(fldEmail.getText().isEmpty()){
            fldEmail.getStyleClass().add("myborderregion");
        }

        if(fldPass.getText().isEmpty()){
            fldPass.getStyleClass().add("myborderregion");
        }

        if(!errorText.equals("")){
            errorLabel.setText(errorText);
            return;
        }
            for (User u : users) {
                if (u.getUsername().equals(fldUsername.getText())) {
                    fldUsername.getStyleClass().add("myborderregion");
                    errorText=errorText+"*This username is already used\n";
                }
            }

        if(!validEmail(fldEmail.getText())){
            fldEmail.getStyleClass().add("myborderregion");
            errorText=errorText+"*Incorrect e-mail\n";
        }

        if(!validPass(fldPass.getText())){
            fldPass.getStyleClass().add("myborderregion");
            errorText=errorText+"*Password should contains\nat least 8 characters,\none number and one capital letter";
        }

        errorLabel.setText(errorText);
        if(!errorText.equals(""))
            return;

        User user = new User(fldFirstName.getText(),fldLastName.getText(),fldEmail.getText(),fldUsername.getText(),fldPass.getText());

        dao.addUser(user);
        signInFun();
    }

    private boolean validEmail(String emailToCheck){
        if(!emailToCheck.contains("@"))
            return false;
        else{
            if(emailToCheck.indexOf('@')==emailToCheck.length()-1 || emailToCheck.indexOf('@')==0)
                return false;
        }

        return true;
    }

    private boolean validPass(String password){
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

    private boolean isNumber(char c){
        return (c>='0' && c<='9');
    }

    private boolean isCapitalLetter(char l){
        return l>='A' && l<='Z';
    }
}
