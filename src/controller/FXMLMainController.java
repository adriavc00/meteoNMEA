package controller;

import java.io.IOException;
import java.net.URL;
import util.Clock;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ADRIA - LP
 */
public class FXMLMainController implements Initializable {

    private final Clock clock = new Clock();

    private Node numInfo;

    @FXML
    private BorderPane mainPane;
    @FXML
    private Text time;
    @FXML
    private Text date;
    @FXML
    private Text day;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Clock initialization
        clock.initClock();
        // LOAD Center Nodes
        try {
            FXMLLoader numLoader = new FXMLLoader(
                    getClass().getResource("/view/FXMLNumericInfo.fxml"));
            this.numInfo = numLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(FXMLMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Set default center node
        mainPane.setCenter(numInfo);
        // Bindings
        time.textProperty().bind(clock.timeProperty());
        date.textProperty().bind(clock.dateProperty());
        day.textProperty().bind(clock.dayProperty());
    }

    @FXML
    private void exit(MouseEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

}
