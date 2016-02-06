package net.davidog.tbcombat.view_controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public InitController controller;

    public Main(){}

    @Override
    public void start(Stage primaryStage) throws Exception{
        initApp(primaryStage);

        primaryStage.setTitle("TB Combat");
        primaryStage.show();
    }

    public void initApp(Stage primaryStage) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Init.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setStage(primaryStage);
        primaryStage.setScene(new Scene(root));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
