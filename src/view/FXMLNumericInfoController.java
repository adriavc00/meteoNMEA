package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author lipez
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
