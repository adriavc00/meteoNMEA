package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import model.Model;

/**
 * FXML Controller class of the numeric information caught from the input data.
 *
 * @author Adria V.
 * @author Felipe Z.
 */
public class FXMLNumericInfoController implements Initializable {

    // File that is loaded every times the app start.
    private static final String DEFAULT_FILE_PATH = "src/files/Jul_20_2017_0183.NMEA";
    // Precision of the pressure data.
    private static final int PRESSURE_CHARS = 6;

    private Model model;

    @FXML
    private Text nTemp;
    @FXML
    private Text nDirection;
    @FXML
    private Text nPressure;
    @FXML
    private Text nSpeed;

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

        try {
            cargarFichero();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLMainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //==================================================================
        // anyadimos listener para que cuando cambie el valor en el modelo
        // se actualice su valor en su correspondiente representacion grafica
        model.barometricPressureProperty().addListener((observable, oldV, newV) -> {
            String dat = String.valueOf(newV);
            String dat0 = dat + "0"; // Variable separada por funcionamiento de lambda.
            Platform.runLater(() -> {
                nPressure.setText((dat.length() < PRESSURE_CHARS) ? dat0 : dat);
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

    private void cargarFichero() throws FileNotFoundException {
        File file = new File(DEFAULT_FILE_PATH);
        model.addSentenceReader(file);
    }
}
