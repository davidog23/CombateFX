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

        primaryStage.setTitle("TB Combat");
        primaryStage.show();
    }

    public void initApp() throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Init.fxml"));
        loader.setControllerFactory(clazz -> {
            if (clazz == InitController.class) {
                return new InitController(this.primaryStage);
            } else {
                // default behavior:
                try {
                    return clazz.newInstance();
                } catch (Exception exc) {
                    throw new RuntimeException(exc);
                }
            }
        });
        Parent root = loader.load();
        controller = loader.getController();
        primaryStage.setScene(new Scene(root));
    }

    public Main getMain() {
        return this;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
