package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.Clock;

/**
 * FXML Controller class
 *
 * @author ADRIA - LP
 */
public class FXMLMainController implements Initializable {

    private final Clock clock = new Clock();

    private Node numInfo;
    private Node tempChart;
    private Node windChart;

    @FXML
    private BorderPane mainPane;
    @FXML
    private Text time;
    @FXML
    private Text date;
    @FXML
    private Text day;
    @FXML
    private ToggleButton themeToggle;
    @FXML
    private Text statusText;
    @FXML
    private Button numericButton;
    @FXML
    private Button windButton;
    @FXML
    private Button tempButton;
    @FXML
    private Button pressureButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set default theme
        String theme;
        if (LocalTime.now().isAfter(LocalTime.of(18, 30))) {
            theme = "darkTheme";
            themeToggle.setSelected(true);
        } else {
            theme = "lightTheme";
            themeToggle.setSelected(false);
        }
        mainPane.getStylesheets().add(
                getClass().getResource("/resources/css/" + theme + ".css").toExternalForm());
        // Clock initialization
        clock.initClock();
        // Load Center Nodes
        try {
            FXMLLoader customLoader = new FXMLLoader(
                    getClass().getResource("/view/FXMLNumericInfo.fxml"));
            this.numInfo = customLoader.load();
            
            customLoader = new FXMLLoader(
                    getClass().getResource("/view/FXMLTempChart.fxml"));
            
            this.tempChart = customLoader.load();
            
            customLoader = new FXMLLoader(
                    getClass().getResource("/view/FXMLWindChart.fxml"));
            
            this.windChart = customLoader.load();
            
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Set default center node
        mainPane.setCenter(numInfo);
        
        numericButton.setDisable(true);
        tempButton.setDisable(false);
        windButton.setDisable(false);
        pressureButton.setDisable(false);
        // Bindings
        time.textProperty().bind(clock.timeProperty());
        date.textProperty().bind(clock.dateProperty());
        day.textProperty().bind(clock.dayProperty());
        // Listeners
        themeToggle.selectedProperty().addListener((observable, oldV, newV) -> {
            if (newV) {
                mainPane.getStylesheets().remove(
                        getClass().getResource("/resources/css/lightTheme.css").toExternalForm());
                mainPane.getStylesheets().add(
                        getClass().getResource("/resources/css/darkTheme.css").toExternalForm());
            } else {
                mainPane.getStylesheets().add(
                        getClass().getResource("/resources/css/lightTheme.css").toExternalForm());
                mainPane.getStylesheets().remove(
                        getClass().getResource("/resources/css/darkTheme.css").toExternalForm());
            }
        });
    }

    @FXML
    private void exit(MouseEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    private void numericScene(ActionEvent event) {
        mainPane.setCenter(numInfo);
        numericButton.setDisable(true);
        tempButton.setDisable(false);
        windButton.setDisable(false);
        pressureButton.setDisable(false);
    }

    @FXML
    private void windScene(ActionEvent event) {
        mainPane.setCenter(windChart);
        numericButton.setDisable(false);
        tempButton.setDisable(false);
        windButton.setDisable(true);
        pressureButton.setDisable(false);
    }

    @FXML
    private void tempScene(ActionEvent event) {
        mainPane.setCenter(tempChart);
        numericButton.setDisable(false);
        tempButton.setDisable(true);
        windButton.setDisable(false);
        pressureButton.setDisable(false);
    }

    @FXML
    private void pressureScene(ActionEvent event) {
        //FALTA DE HACER EL FXML
        
        numericButton.setDisable(false);
        tempButton.setDisable(false);
        windButton.setDisable(false);
        pressureButton.setDisable(true);
    }

}
