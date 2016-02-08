package net.davidog.tbcombat.view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import net.davidog.tbcombat.model.SocketWrapper;
import net.davidog.tbcombat.utils.Util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class InitController {

    private Stage stage;
    private SocketWrapper connectionToServer;
    private boolean debug;

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
    public void onlineTrigger(ActionEvent event) {
        SocketWrapper server;
        try {
            server = loadServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SocketWrapper loadServer() throws IOException {
        connectionToServer = new SocketWrapper();
        SelectServerController controller = Util.loadWindowWithArgument(SelectServerController.class, getClass().getResource("SelectServer.fxml"), connectionToServer);
        Stage serverMStage = controller.getStage();
        serverMStage.setTitle("Server Management");
        serverMStage.showAndWait();
        return connectionToServer;
    }

    public void init(boolean debug) {
        if(debug) {
            System.setOut(new PrintStream(new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                    initConsole.appendText(String.valueOf((char) b));
                }
            }));
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
