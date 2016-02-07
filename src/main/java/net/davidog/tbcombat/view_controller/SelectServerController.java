package net.davidog.tbcombat.view_controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import net.davidog.tbcombat.model.SocketWrapper;
import net.davidog.tbcombat.utils.GsonUtil;
import net.davidog.tbcombat.utils.Reference;
import net.davidog.tbcombat.utils.ServerInfo;
import net.davidog.tbcombat.utils.Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * IDEA: Crear los stages en los controller para poder sobreescribir el close(). A demas elimina la necesidad que pasar el stage al controller como parametro.
 * Controller for server selection.
 * Created by David on 02/02/2016.
 */
public class SelectServerController implements IGameController {
    private final Stage stage = new Stage() {
        @Override
        public void close() {
            try {
                GsonUtil.writeGson(new File(Reference.SERVER_INFO_PATH), serverData);
            } catch (IOException e) {
                e.printStackTrace();
            }
            super.close();
        }
    };

    private ObservableList<ServerInfo> serverData;
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

    public SelectServerController() {}
    
    @FXML
    void initialize() throws IOException {
        Path serverFilePath = Paths.get(Reference.SERVER_INFO_PATH);
        if(Files.notExists(serverFilePath.getParent())) { Files.createDirectory(serverFilePath.getParent()); }
        if(Files.exists(serverFilePath)) {
            serverData.setAll(GsonUtil.leerGson(serverFilePath.toFile(), ServerInfo.class));
        } else {
            serverData = FXCollections.emptyObservableList();
        }
        name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        address.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        port.setCellValueFactory(cellData -> cellData.getValue().portProperty());
    }

    @FXML
    private void addServerHandler(ActionEvent event) {
        try {
            ServerAdderController adderController = Util.loadWindowWithArgument(ServerAdderController.class, Main.class.getResource("ServerAdder.fxml"), serverData);
            adderController.getStage().setTitle("Añadir Servidor");
            adderController.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void removeServerHandler() {

    }

    public SocketWrapper getServerSelected() {
        return this.serverSelected;
    }

    public Stage getStage() {
        return stage;
    }
}
