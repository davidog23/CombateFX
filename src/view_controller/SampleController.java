package view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import model.Jugador;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;

public class SampleController {

    private Jugador player;
    private Jugador target;

    private int atackSelected;

    private HashMap<Button, Integer> map = new HashMap<>();

    @FXML
    private TextArea txtConsole;
    @FXML
    private Button atq1Btn;
    @FXML
    private Button atq2Btn;
    @FXML
    private Button atq3Btn;
    @FXML
    private Button atq4Btn;
    @FXML
    private Button atq5Btn;

    private Button[] buttons = {atq1Btn, atq2Btn, atq3Btn, atq4Btn, atq5Btn};

    @FXML
    void initialize()
    {
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                txtConsole.appendText(String.valueOf((char) b));
                txtConsole.end();
            }
        }));
        map.put(atq1Btn, 1);
        map.put(atq2Btn, 2);
        map.put(atq3Btn, 3);
        map.put(atq4Btn, 4);
        map.put(atq5Btn, 5);
    }

    @FXML
    private void onButtonClick(ActionEvent event)
    {
        atackSelected = map.get((Button) event.getSource());
        System.out.println(atackSelected);
    }

    public void setPlayers(Jugador player, Jugador target)
    {
        this.player = player;
        this.target = target;
    }

    public void setDisableButtons(boolean bool)
    {
        for (Button b : buttons)
        {
            b.setDisable(bool);
        }
    }
}
