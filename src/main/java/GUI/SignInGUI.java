package main.java.GUI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import main.java.Domain.User;
import main.java.Retrofit.ApiInterface;

import javafx.scene.control.TextField;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class SignInGUI {
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private PasswordField retryPasswordTextField;
    private Stage stage;
    private ApiInterface apiInterface;
    public void initializare(Stage stage, ApiInterface apiInterface) {

        this.stage=stage;
        this.apiInterface=apiInterface;
    }

    public void LogInShow() throws IOException {

        Parent root;
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(LoginGUI.class.getResource("/Login.fxml"));
        LoginGUI afis=new LoginGUI(stage,apiInterface);
        loader.setController(afis);
        root = loader.load();
        stage.setTitle("Login");
        stage.setScene(new Scene(root ));
    }
    public void SignUp(){

        if(Validation()){


            User user=new User(emailTextField.getText(),passwordTextField.getText(),usernameTextField.getText());

            Call<Void> call=apiInterface.SignUp(user);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.code()==201)
                    {

                        Platform.runLater(()->{
                            try {
                                SuccesSignUp();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

                    }
                    else {


                        Platform.runLater(()->{
                            ShowLoginWrong();
                        });
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Platform.runLater(()->{
                        ShowLoginError(String.valueOf(t));
                    });
                }
            });

        }


    }
    public void SuccesSignUp() throws IOException {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SignIn Succesfull");
        alert.setHeaderText("User Created!");
        alert.setContentText("User created, please log in.");

        alert.showAndWait();
        LogInShow();


    }


    public void ShowLoginWrong(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("SignIn Error");
        alert.setHeaderText("Something went wrong.");
        alert.setContentText("User already exist or invalid data!");

        alert.showAndWait();
    }
    public void ShowLoginError(String t){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Signin Error");
        alert.setHeaderText("Connexion Error!");
        alert.setContentText(t);

        alert.showAndWait();

    }

    public boolean Validation(){

        if(emailTextField.getText().equals("")){
            ShowSigninWrong("Email cant be empty!");
            return false;
        }

        if(usernameTextField.getText().equals("")){
            ShowSigninWrong("Username cant be empty!");
            return false;
        }

        if(passwordTextField.getText().equals("")){
            ShowSigninWrong("Password cant be empty!");
            return false;
        }
        if(!(passwordTextField.getText().equals(retryPasswordTextField.getText()))){
            ShowSigninWrong("Passwords dont match!!");
            return false;
        }

        return true;
    }
    public void ShowSigninWrong(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("SignIn Error");
        alert.setHeaderText("Something went wrong.");
        alert.setContentText(message);

        alert.showAndWait();
    }
}
