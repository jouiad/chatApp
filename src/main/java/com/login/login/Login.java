package com.login.login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Login extends Application {
    public static void main(String[] args) {
        launch();
    }

    // Set up a stage to show the window
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("views/login-view.fxml"));
        // FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("views/register-view.fxml"));
        // FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("views/panel-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login Form");
        stage.setScene(scene);
        stage.show();
    }
}