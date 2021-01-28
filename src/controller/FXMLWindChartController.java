package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;
import model.Model;

/**
 * FXML Controller class of the wind charts
 *
 * @author Adria V.
 * @author Felipe Z.
 */
public class FXMLWindChartController implements Initializable {

    private XYChart.Series<String, Number> chartDirectionSerie;
    private Model model;

    @FXML
    private LineChart<String, Number> chartDirection;
    @FXML
    private LineChart<String, Number> chartSpeed;
    private XYChart.Series<String, Number> chartSpeedSerie;
    @FXML
    private Slider slider;
    @FXML
    private Text nDirection;
    @FXML
    private Text nSpeed;
    @FXML
    private Text nTemp;
    @FXML
    private Text nPressure;

    /**
     * Initialize the controller class.
     *
     * @param url The location used to resolve relative paths for the root object, or <tt>null</tt>
     * if the location is not known.
     *
     * @param rb  The resources used to localize the root object, or <tt>null</tt> if the root
     *            object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = Model.getInstance();

        slider.valueProperty().addListener((observable, oldV, newV) -> {
            int size = (int) Math.round((double) newV * 60);
            model.setSizeWindChart(size);
        });

        model.setSizeWindChart(300);
        chartDirectionSerie = model.getTWDSerie();
        chartDirectionSerie.setName("Dirección (º)");

        chartSpeedSerie = model.getTWSSerie();
        chartSpeedSerie.setName("Velocidad (Kn)");

        // Hide X axis, doable from FXML (cleaner and preferred)
        chartSpeed.getXAxis().setTickLabelsVisible(false);
        chartSpeed.getXAxis().setTickMarkVisible(false);

        chartDirection.getData().add(chartDirectionSerie);
        chartSpeed.getData().add(chartSpeedSerie);

        model.barometricPressureProperty().addListener((observable, oldV, newV) -> {
            String dat = String.valueOf(newV);
            Platform.runLater(() -> {
                nPressure.setText(dat);
            });
        });
        model.TEMPProperty().addListener((observable, oldV, newV) -> {
            String dat = String.valueOf(newV);
            Platform.runLater(() -> {
                nTemp.setText(dat);
            });
        });
        model.TWDProperty().addListener((observable, oldV, newV) -> {
            String dat = String.valueOf(newV);
            Platform.runLater(() -> {
                nDirection.setText(dat);
            });
        });
        model.TWSProperty().addListener((observable, oldV, newV) -> {
            String dat = String.valueOf(newV);
            Platform.runLater(() -> {
                nSpeed.setText(dat);
            });
        });
    }

}
