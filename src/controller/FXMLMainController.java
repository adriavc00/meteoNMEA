package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.Clock;
import util.Theme;

/**
 * FXML Controller class
 *
 * @author ADRIA - LP
 */
public class FXMLMainController implements Initializable {

    private final Map<String, Initializable> controllers = new HashMap<String, Initializable>(6);
    private final Clock clock = new Clock();

    private final SimpleObjectProperty<Theme> currentTheme = new SimpleObjectProperty<Theme>();

    private Node numInfo;
    private Node tempChart;
    private Node windChart;
    private Node pressureChart;
    private Node configuration;
    private Node noImplement;

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
    @FXML
    private VBox toolbar;
    @FXML
    private ImageView confButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set default theme
        String theme;
        if (LocalTime.now().isAfter(LocalTime.of(18, 30))
                || LocalTime.now().isBefore(LocalTime.of(7, 0))) {
            theme = "darkTheme";
            themeToggle.setSelected(true);
            currentTheme.set(Theme.DARK_THEME);
        } else {
            theme = "lightTheme";
            themeToggle.setSelected(false);
            currentTheme.set(Theme.LIGHT_THEME);
        }
        mainPane.getStylesheets().add(
                getClass().getResource("/resources/css/" + theme + ".css").toExternalForm());
        initializeIcons();
        // Clock initialization
        clock.initClock();
        // Load Center Nodes
        try {
            FXMLLoader customLoader = new FXMLLoader(
                    getClass().getResource("/view/FXMLNumericInfo.fxml"));
            this.numInfo = customLoader.load();
            controllers.put("numInfo", customLoader.getController());

            customLoader = new FXMLLoader(
                    getClass().getResource("/view/FXMLTempChart.fxml"));
            this.tempChart = customLoader.load();
            controllers.put("tempChart", customLoader.getController());

            customLoader = new FXMLLoader(
                    getClass().getResource("/view/FXMLWindChart.fxml"));
            this.windChart = customLoader.load();
            controllers.put("windChart", customLoader.getController());

            customLoader = new FXMLLoader(
                    getClass().getResource("/view/FXMLPressureChart.fxml"));
            this.pressureChart = customLoader.load();
            controllers.put("pressureChart", customLoader.getController());

            customLoader = new FXMLLoader(
                    getClass().getResource("/view/FXMLConfiguration.fxml"));
            this.configuration = customLoader.load();
            controllers.put("configuration", customLoader.getController());

            customLoader = new FXMLLoader(
                    getClass().getResource("/view/FXMLNoImplement.fxml"));
            this.noImplement = customLoader.load();
            controllers.put("noImplement", customLoader.getController());
        } catch (IOException ex) {
            Logger.getLogger(FXMLMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Set default center node
        mainPane.setCenter(numInfo);
        // Initial toolbar configuration
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
                currentTheme.set(Theme.DARK_THEME);
                initializeIcons();
            } else {
                mainPane.getStylesheets().add(
                        getClass().getResource("/resources/css/lightTheme.css").toExternalForm());
                mainPane.getStylesheets().remove(
                        getClass().getResource("/resources/css/darkTheme.css").toExternalForm());
                currentTheme.set(Theme.LIGHT_THEME);
                initializeIcons();
            }
        });
    }

    public SimpleObjectProperty<Theme> themeProperty() {
        return currentTheme;
    }

    public void setConfigurationCenter() {
        mainPane.setCenter(configuration);
        statusText.setText("Configuración");
    }

    public void setNoImplementCenter() {
        this.mainPane.setCenter(noImplement);
        this.statusText.setText("");
        ((FXMLNoImplementController) controllers.get("noImplement")).setMainController(this);
    }

    public void setNumericCenter() {
        this.restoreToolbar();
        numericButton.setDisable(false);
        numericButton.fire();
        confButton.setImage(new Image("/resources/images/white_connection_icon.png"));
        confButton.setOnMouseClicked(this::configurationScene);
    }

    private void restoreToolbar() {
        this.mainPane.setLeft(toolbar);
        ((Pane) mainPane.getBottom()).getChildren().get(0).getStyleClass().add("toolbar");
    }

    private void initializeIcons() {
        switch (this.currentTheme.get()) {
            case DARK_THEME:
                numericButton.setGraphic(new ImageView(new Image(
                        "/resources/images/white_num_icon.png", 40, 40, false, false)));
                tempButton.setGraphic(new ImageView(new Image(
                        "/resources/images/white_temperature_icon.png", 40, 40, false, false)));
                windButton.setGraphic(new ImageView(new Image(
                        "/resources/images/white_wind_icon.png", 40, 40, false, false)));
                pressureButton.setGraphic(new ImageView(new Image(
                        "/resources/images/white_pressure_icon.png", 40, 40, false, false)));
                break;
            case LIGHT_THEME:
                numericButton.setGraphic(new ImageView(new Image(
                        "/resources/images/black_num_icon.png", 40, 40, false, false)));
                tempButton.setGraphic(new ImageView(new Image(
                        "/resources/images/black_temperature_icon.png", 40, 40, false, false)));
                windButton.setGraphic(new ImageView(new Image(
                        "/resources/images/black_wind_icon.png", 40, 40, false, false)));
                pressureButton.setGraphic(new ImageView(new Image(
                        "/resources/images/black_pressure_icon.png", 40, 40, false, false)));
                break;
        }
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
        // Change status text
        statusText.setText("Datos numéricos | Simulación");
    }

    @FXML
    private void windScene(ActionEvent event) {
        mainPane.setCenter(windChart);
        numericButton.setDisable(false);
        tempButton.setDisable(false);
        windButton.setDisable(true);
        pressureButton.setDisable(false);
        // Change status text
        statusText.setText("Variación del viento | Simulación");
    }

    @FXML
    private void tempScene(ActionEvent event) {
        mainPane.setCenter(tempChart);
        numericButton.setDisable(false);
        tempButton.setDisable(true);
        windButton.setDisable(false);
        pressureButton.setDisable(false);
        // Change status text
        statusText.setText("Variación de la temperatura | Simulación");
    }

    @FXML
    private void pressureScene(ActionEvent event) {
        mainPane.setCenter(pressureChart);
        numericButton.setDisable(false);
        tempButton.setDisable(false);
        windButton.setDisable(false);
        pressureButton.setDisable(true);
        // Change status text
        statusText.setText("Variación de la presión | Simulación");
    }

    @FXML
    private void configurationScene(MouseEvent event) {
        confButton.setImage(new Image("/resources/images/white_home_icon.png"));
        confButton.setOnMouseClicked((e) -> this.setNumericCenter());
        mainPane.setLeft(null);
        ((Pane) mainPane.getBottom()).getChildren().get(0).getStyleClass().remove("toolbar");
        mainPane.setCenter(configuration);
        statusText.setText("Configuración");
        ((FXMLConfigurationController) controllers.get("configuration")).setMainController(this);
    }
}
