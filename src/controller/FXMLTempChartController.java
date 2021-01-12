package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author lipez
 */
public class FXMLTempChartController implements Initializable {

    @FXML
    private LineChart<?, ?> chartDirection;
    @FXML
    private Slider slider;
    @FXML
    private Text nDirection;
    @FXML
    private Text nSpeed;
    @FXML
    private Text nTemp;
    @FXML
    private Text nTemp1;
    @FXML
    private Text nPressure;
    @FXML
    private Text nPressure1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
