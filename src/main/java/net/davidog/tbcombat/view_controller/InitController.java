package net.davidog.tbcombat.view_controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import net.davidog.tbcombat.model.SocketWrapper;
import net.davidog.tbcombat.utils.Util;
import net.davidog.tbcombat.utils.Window;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class InitController implements IGameController{

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
    private void onlineTrigger(ActionEvent event) {
        SocketWrapper server = loadServer();
    }

    private SocketWrapper loadServer() {
        Window<SelectServerController> window;
        try {
            window = Util.loadWindow(SelectServerController.class, getClass().getResource("SelectServer.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
