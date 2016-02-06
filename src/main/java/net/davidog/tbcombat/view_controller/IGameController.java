package net.davidog.tbcombat.view_controller;

import javafx.stage.Stage;

/**
 * Interface para controllers.
 * TODAS las clases que implementen esta interface deben tener un constructor del tipo T(Stage)
 * Created by David on 13/01/2016.
 */
public interface IGameController {
    Stage getStage();
}
