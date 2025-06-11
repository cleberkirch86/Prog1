package org.example.atividade2_rev02;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CarrosApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/atividade2_rev02/Carros.fxml"));
        primaryStage.setTitle("Cadastro de Carros");
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    public static void iniciar() {
        launch();
    }
}

