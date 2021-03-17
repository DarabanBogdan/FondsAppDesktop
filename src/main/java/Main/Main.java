package main.java.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.GUI.LoginGUI;
import main.java.Retrofit.ApiClient;
import main.java.Retrofit.ApiInterface;



public class Main extends Application {

    ApiInterface apiInterface;
    @Override
    public void start(Stage primaryStage) throws Exception{

        apiInterface= ApiClient.getClient().create(ApiInterface.class);


        Parent root;
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(Main.class.getResource("/LogIn.fxml"));
        LoginGUI stage=new LoginGUI(primaryStage,apiInterface);

        loader.setController(stage);
        root = loader.load();
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root ));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
