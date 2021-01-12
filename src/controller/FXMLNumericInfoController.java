package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author ADRIA - LP
 */
public class FXMLNumericInfoController implements Initializable {

    @FXML
    private Text nTemp;
    @FXML
    private Text nDirection;
    @FXML
    private Text nPressure;
    @FXML
    private Text nSpeed;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
