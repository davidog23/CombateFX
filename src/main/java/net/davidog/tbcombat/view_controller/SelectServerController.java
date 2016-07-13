package net.davidog.tbcombat.view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import net.davidog.tbcombat.network.SocketWrapper;
import net.davidog.tbcombat.utils.ServerInfo;
import net.davidog.tbcombat.utils.Util;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

/**
 * IDEA: Crear los stages en los controller para poder sobreescribir el close(). A demas elimina la necesidad que pasar el stage al controller como parametro.
 * Controller for server selection.
 * Created by David on 02/02/2016.
 */
public class SelectServerController implements IGameController {
    private Stage stage;

    private SocketWrapper serverSelected;

    @FXML
    private TableView<ServerInfo> table;

    @FXML
    private TableColumn<ServerInfo, String> name;
    @FXML
    private TableColumn<ServerInfo, String> address;
    @FXML
    private TableColumn<ServerInfo, Number> port;

    @FXML
    private Button addServerBtn;
    @FXML
    private Button removeServerBtn;
    @FXML
    private Button connectBtn;
    @FXML
    private Button directConnectBtn;
    private Main appInstance;

    public SelectServerController(SocketWrapper wrapperToFill, Main appInstance) {
        this.appInstance = appInstance;
        stage = new Stage();
        serverSelected = wrapperToFill;
    }
    public SelectServerController() {
        stage = new Stage();
    }
    
    @FXML
    void initialize() throws IOException {
        stage.setOnCloseRequest(event -> ((Stage)event.getSource()).close());

        name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        address.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        port.setCellValueFactory(cellData -> cellData.getValue().portProperty());

        table.setItems(appInstance.getServerData());
    }

    @FXML
    private void addServerHandler(ActionEvent event) {
        try {
            if (event.getSource() == addServerBtn) {
                ServerAdderController adderController = Util.loadWindowWithArgument(ServerAdderController.class, Main.class.getResource("ServerAdder.fxml"), appInstance.getServerData());
                adderController.getStage().setTitle("Añadir Servidor");
                adderController.getStage().showAndWait();
            } else if(event.getSource() == directConnectBtn) {
                ServerAdderController adderController = Util.loadWindowWithArgument(ServerAdderController.class, Main.class.getResource("ServerAdder.fxml"), serverSelected);
                adderController.getStage().setTitle("Conectar con servidor");
                adderController.getStage().showAndWait();
                if(!serverSelected.isVoid()) {
                    stage.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void removeServerHandler(ActionEvent event) {
        int index = table.getSelectionModel().getSelectedIndex();
        table.getItems().remove(index);
    }

    @FXML
    private void connectHandler(ActionEvent event) throws IOException {
        ServerInfo infoServer = table.getItems().get(table.getSelectionModel().getSelectedIndex());
        try {
            serverSelected.initialize(new Socket(infoServer.getAddress(), infoServer.getPort()), false);
        } catch (ConnectException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Connection timeout");
            error.setHeaderText("Se ha excedido el tiempo de intento de conexión.");
            error.setContentText("Es posible que el servidor no esté disponible.");
            error.showAndWait();
            serverSelected.setVoid(false); //For debug persuposes.
        }
    }

    public SocketWrapper getServerSelected() {
        return this.serverSelected;
    }

    public Stage getStage() {
        return stage;
    }
}
