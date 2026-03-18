package com.clinique;

import com.clinique.util.InitData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        InitData.initialiser();

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/clinique/views/login.fxml")
        );
        Scene scene = new Scene(loader.load(), 400, 300);
        stage.setTitle("Clinique Médicale");
        stage.setScene(scene);
        stage.show();
    }
}