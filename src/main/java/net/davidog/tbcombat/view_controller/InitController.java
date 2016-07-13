package net.davidog.tbcombat.view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import net.davidog.tbcombat.network.ServerCombate;
import net.davidog.tbcombat.network.SocketWrapper;
import net.davidog.tbcombat.utils.Util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Optional;

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
    private void loadServer(ActionEvent event) throws IOException {
        TextInputDialog input = new TextInputDialog();
        input.setTitle("Port chooser");
        input.setHeaderText("Elige el puerto en el que corre el servidor.");
        input.setContentText("Introduce el puerto: ");
        Optional<String> result = input.showAndWait();
        //Mirarcomo voy a conectar la vista con el modelo del server.
        ServerCombate.runServer(Integer.parseInt(result.orElse("8080")));
    }

    @FXML
    private void onlineTrigger(ActionEvent event) { //WIP
        SocketWrapper server;
        try {
            server = connectToHost();
            new Object();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SocketWrapper connectToHost() throws IOException {
        SocketWrapper connectionToServer = new SocketWrapper();
        SelectServerController selectServerController = Util.loadWindowWithArgument(SelectServerController.class, getClass().getResource("SelectServer.fxml"), connectionToServer, appInstance);
        Stage serverMStage = selectServerController.getStage();
        serverMStage.setTitle("Server Management");
        serverMStage.showAndWait();
        return connectionToServer;
    }

    private void setConsoleOutput() {
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                initConsole.appendText(String.valueOf((char) b));
            }
        }));
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
