package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.Model;

/**
 * FXML Controller class
 *
 * @author lipez
 */
public class FXMLWindChartController implements Initializable {

    @FXML
    private LineChart<String, Number> chartDirection;
    private XYChart.Series<String, Number> chartDirectionSerie;

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

    private Model model;
    @FXML
    private Text nTemp1;
    @FXML
    private Text nPressure1;
    @FXML
    private Text nTemp11;
    @FXML
    private Text nTemp12;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        model = Model.getInstance();
        //int size;

        slider.valueProperty().addListener((a, b, c) -> {
            int size = (int) Math.round((double) c * 60);
            //System.out.println(size);
            model.setSizeWindChart(size);
        });

        model.setSizeWindChart(300);

        chartDirectionSerie = model.getTWDSerie();
        chartDirectionSerie.setName("Dirección (º)");

        chartSpeedSerie = model.getTWSSerie();
        chartSpeedSerie.setName("Velocidad (Kn)");

        //chartDirection.getXAxis().setAutoRanging(false);
        //chartDirection.getXAxis().setTickLabelsVisible(false);
        //chartDirection.getXAxis().setTickMarkVisible(false);
        //chartDirection.getXAxis().setOpacity(0);
        //chartDirection.getXAxis().setLabel("Dirección");
        chartSpeed.getXAxis().setTickLabelsVisible(false);
        chartSpeed.getXAxis().setTickMarkVisible(false);
        //chartSpeed.getXAxis().setOpacity(0);
        //chartSpeed.getXAxis().setLabel("Velocidad");

        chartDirection.getData().add(chartDirectionSerie);
        chartSpeed.getData().add(chartSpeedSerie);

        //INFORMACIÓN NUMÉRICA
        model.barometricPressureProperty().addListener((a, b, c) -> {
            String dat = String.valueOf(c);
            // + " " + model.getBarometricUnit()
            Platform.runLater(() -> {
                nPressure.setText(dat);
            });
        });
        model.TEMPProperty().addListener((a, b, c) -> {
            String dat = String.valueOf(c);
            Platform.runLater(() -> {
                nTemp.setText(dat);
            });
        });

        model.TWDProperty().addListener((a, b, c) -> {
            String dat = String.valueOf(c);
            Platform.runLater(() -> {
                nDirection.setText(dat);
            });
        });
        model.TWSProperty().addListener((a, b, c) -> {
            String dat = String.valueOf(c);
            Platform.runLater(() -> {
                nSpeed.setText(dat);
            });
        });
    }

}
