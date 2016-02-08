package net.davidog.tbcombat.utils;

import javafx.stage.Stage;
import net.davidog.tbcombat.view_controller.IGameController;

/**
 * Wrapper for Stage and Controller. Soon deprecated. Stages inside its controller.
 * Created by David on 01/02/2016.
 */
@Deprecated
public class Window<T extends IGameController> {
    private Stage stage;
    private T controller;

    public Window(T controller, Stage stage) {
        this.controller = controller;
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public T getController() {
        return controller;
    }
}
