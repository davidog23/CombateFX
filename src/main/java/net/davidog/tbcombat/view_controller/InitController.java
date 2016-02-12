package net.davidog.tbcombat.view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import net.davidog.tbcombat.model.SocketWrapper;
import net.davidog.tbcombat.utils.Util;

import java.io.IOException;

public class InitController {

    private Stage stage;
    private Main appInstance;

    @FXML
    private TextArea initConsole;

    @FXML
    private Button online;
    @FXML
    private Button offline;
    @FXML
    private Button server;

    public InitController() {}

    @FXML
    void initialize() {

    }


    @FXML
    public void onlineTrigger(ActionEvent event) { //WIP
        SocketWrapper server;
        try {
            server = loadServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SocketWrapper loadServer() throws IOException {
        SocketWrapper connectionToServer = new SocketWrapper();
        SelectServerController selectServerController = Util.loadWindowWithArgument(SelectServerController.class, getClass().getResource("SelectServer.fxml"), connectionToServer, appInstance);
        Stage serverMStage = selectServerController.getStage();
        serverMStage.setTitle("Server Management");
        serverMStage.showAndWait();
        return connectionToServer;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setAppInstance(Main appInstance) {
        this.appInstance = appInstance;
    }
}
