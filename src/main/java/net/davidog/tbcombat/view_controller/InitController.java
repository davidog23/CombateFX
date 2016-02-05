package net.davidog.tbcombat.view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import net.davidog.tbcombat.model.SocketWrapper;
import net.davidog.tbcombat.utils.Util;
import net.davidog.tbcombat.utils.Window;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

public class InitController implements IGameController{

    private Stage stage;
    private boolean debug;

    @FXML
    private TextArea initConsole;

    @FXML
    private Button online;
    @FXML
    private Button offline;
    @FXML
    private Button server;

    public InitController(Stage stage) {
        this.stage = stage;
    }

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
        Window<SelectServerController> window = Util.loadWindow(SelectServerController.class, getClass().getResource("SelectServer.fxml"));
        Stage serverMStage = window.getStage();
        serverMStage.setTitle("Server Management");
        serverMStage.showAndWait();
        return window.getController().getServerSelected();
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

}
