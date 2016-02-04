package net.davidog.tbcombat.view_controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import net.davidog.tbcombat.model.SocketWrapper;
import net.davidog.tbcombat.utils.GsonUtil;
import net.davidog.tbcombat.utils.Reference;
import net.davidog.tbcombat.utils.ServerInfo;
import net.davidog.tbcombat.utils.Window;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Controller for server selection.
 * Created by David on 02/02/2016.
 */
public class SelectServerController implements IGameController {
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
        Window<ServerAdderController> popup;
    }

    public SocketWrapper getServerSelected() {
        return this.serverSelected;
    }

    private void onClose() throws IOException {
        GsonUtil.writeGson(new File(Reference.SERVER_INFO_PATH), serverData);
    }
}
