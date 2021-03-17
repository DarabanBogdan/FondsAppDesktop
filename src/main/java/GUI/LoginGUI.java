package main.java.GUI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.Domain.User;
import main.java.Retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class LoginGUI {

    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordTextField;



    private Stage stage;
    private ApiInterface apiInterface;

    public LoginGUI(Stage stage, ApiInterface apiInterface) {
        this.stage = stage;
        this.apiInterface = apiInterface;
    }

    public void LogIn(){
        User user=new User(emailTextField.getText(),passwordTextField.getText());

        Call<String> call=apiInterface.Login(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code()==200)
                {
                    user.setToken("Bearer "+response.body());

                    Platform.runLater(()->{
                        try {
                            SetAppWindow(user);
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
            public void onFailure(Call<String> call, Throwable t) {
                Platform.runLater(()->{
                ShowLoginError(String.valueOf(t));
                });
            }
        });
    }


    public void ShowLoginWrong(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText("Something went wrong.");
        alert.setContentText("Email or password dont match!");

        alert.showAndWait();
    }

    public void ShowLoginError(String t){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText("Connexion Error!");
        alert.setContentText(t);

        alert.showAndWait();

    }
    public void SignInShow() throws IOException {
        stage.setTitle(" ");
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(LoginGUI.class.getResource("/SignIn.fxml"));

        Parent root=loader.load();
        SignInGUI signInWindow=loader.getController();
        signInWindow.initializare(stage,this.apiInterface);
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void SetAppWindow(User user) throws IOException {

        stage.setTitle(" ");
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(LoginGUI.class.getResource("/AppWindow.fxml"));

        Parent root=loader.load();
        AppWindowGUI appWindow=loader.getController();
        appWindow.initializare(stage,this.apiInterface,user);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
