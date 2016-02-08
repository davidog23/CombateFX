package net.davidog.tbcombat.view_controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import net.davidog.tbcombat.model.SocketWrapper;
import net.davidog.tbcombat.utils.ServerInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Controller for server add popup.
 * Created by David on 02/02/2016.
 */
public class ServerAdderController implements IGameController{
    private Stage stage;

    private Service<Void> checkerThread;
    private boolean stopThread;

    private boolean directConnect;
    private SocketWrapper selectedServer;

    private ObservableList<ServerInfo> serverData;
    private ServerInfo server;

    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField portField;

    @FXML
    private Label lblValid;
    @FXML
    private Label lblName;
    @FXML
    private Button addBtn;
    @FXML
    private Button cancelBtn;

    public ServerAdderController(Object o) throws IllegalArgumentException {
        this.stopThread = false;

        this.stage = new Stage() {
            @Override
            public void close() {
                stopThread = true;
                super.close();
            }
        };

        if(o instanceof ObservableList) {
            this.serverData = (ObservableList<ServerInfo>) o;
        } else if(o instanceof SocketWrapper) {
            this.directConnect = true;
            this.selectedServer = (SocketWrapper) o;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @FXML
    void initialize(){
        addBtn.setDisable(true);
        if(directConnect) {
            lblName.setDisable(true);
            nameField.setDisable(true);
            addBtn.setText("Connect");
        }

        checkerThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            if (InetAddress.getByName(addressField.getText()).isReachable(1500) && !addressField.getText().equals("") && !portField.getText().equals("")) {
                                server = new ServerInfo(nameField.getText(), addressField.getText(), Integer.parseInt(portField.getText()));
                                Platform.runLater(() -> {
                                    addBtn.setDisable(false);
                                    lblValid.setText("Valid Address");
                                });
                            } else {
                                Platform.runLater(() -> {
                                    addBtn.setDisable(false);
                                    lblValid.setText("Not Connection");
                                });
                            }
                        } catch (IOException e) {
                            if(e instanceof UnknownHostException) {
                                Platform.runLater(() -> {
                                    addBtn.setDisable(true);
                                    lblValid.setText("Invalid Address");
                                });
                            } else {
                                e.printStackTrace();
                            }
                        }
                        return null;
                    }
                };
            }
        };
    }

    @FXML
    private void addServerHandler(ActionEvent event) throws IOException {
        if (!directConnect) {
            serverData.add(server);
        } else {
            selectedServer.initialize(new Socket(server.getAddress(), server.getPort()), false);
        }
        stage.close();
    }

    @FXML
    private void onKeyReleased(KeyEvent event) {
        if(!checkerThread.isRunning()) {
            checkerThread.reset();
            checkerThread.start();
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        stage.close();
    }

    @Override
    public Stage getStage() {
        return stage;
    }
}
