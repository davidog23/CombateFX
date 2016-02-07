package net.davidog.tbcombat.view_controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Controller for server add popup.
 * Created by David on 02/02/2016.
 */
public class ServerAdderController implements IGameController{
    private Stage stage;

    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField portField;

    @FXML
    private Label lblValid;
    @FXML
    private Button addBtn;
    @FXML
    private Button cancelBtn;

    public ServerAdderController() {
        this.stage = new Stage();
    }

    @FXML
    void initialize(){}

    @FXML
    private void onKeyReleased(KeyEvent event) {

    }

    @Override
    public Stage getStage() {
        return stage;
    }
}
