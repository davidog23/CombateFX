package net.davidog.tbcombat.view_controller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.davidog.tbcombat.utils.GsonUtil;
import net.davidog.tbcombat.utils.Reference;
import net.davidog.tbcombat.utils.ServerInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends Application {

    public ObservableList<ServerInfo> serverData;

    public InitController controller;

    public Main(){}

    @Override
    public void start(Stage primaryStage) throws Exception{
        Path serverFilePath = Paths.get(Reference.SERVER_INFO_PATH);
        if(Files.notExists(serverFilePath.getParent())) { Files.createDirectory(serverFilePath.getParent()); }
        if(Files.exists(serverFilePath)) {
            serverData.setAll(GsonUtil.leerGson(serverFilePath.toFile(), ServerInfo.class)); //Guardo observable list y leo ServerInfo OJO
        } else {
            serverData = FXCollections.observableArrayList();
        }

        initApp(primaryStage);

        primaryStage.setTitle("TB Combat");
        primaryStage.show();
    }

    @Override
    public void stop(){
        try {
            GsonUtil.writeGson(new File(Reference.SERVER_INFO_PATH), serverData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initApp(Stage primaryStage) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Init.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setStage(primaryStage);
        controller.setAppInstance(this);
        primaryStage.setScene(new Scene(root));
    }

    public ObservableList<ServerInfo> getServerData() {
        return serverData;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
