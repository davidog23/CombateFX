package net.davidog.tbcombat.view_controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import net.davidog.tbcombat.network.SocketWrapper;
import net.davidog.tbcombat.utils.ServerInfo;

import java.io.IOException;
import java.net.ConnectException;
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

    private boolean directConnect;
    private SocketWrapper selectedServer;

    private ObservableList<ServerInfo> serverData;

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
        this.stage = new Stage();

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
                                Platform.runLater(() -> {
                                    addBtn.setDisable(false);
                                    lblValid.setText("Valid Address");
                                });
                            } else {
                                Platform.runLater(() -> {
                                    if(!addressField.getText().equals("") && !portField.getText().equals("")) {
                                        addBtn.setDisable(false);
                                    } else {
                                        addBtn.setDisable(true);
                                    }
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
            ServerInfo server = new ServerInfo(nameField.getText(), addressField.getText(), Integer.parseInt(portField.getText()));
            try {
                serverData.add(server);
            } catch (UnsupportedOperationException e) {
                e.printStackTrace();
            }
        } else {
            try {
                selectedServer.initialize(new Socket(addressField.getText(), Integer.parseInt(portField.getText())), false);
            } catch (ConnectException e) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Connection timeout");
                error.setHeaderText("Se ha excedido el tiempo de intento de conexión.");
                error.setContentText("Es posible que haya escrito la dirección mal o que el servidor no esté disponible.");
                error.showAndWait();
            }
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

    public Stage getStage() {
        return stage;
    }
}
