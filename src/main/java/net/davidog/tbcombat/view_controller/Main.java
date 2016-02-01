package net.davidog.tbcombat.view_controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public Stage primaryStage;
    public InitController controller;

    public Main(){}

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;

        initApp();

        primaryStage.setTitle("CombateFX");
        primaryStage.show();
    }

    public void initApp() throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Init.fxml"));
        controller = loader.getController();
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
