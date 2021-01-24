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
 * FXML Controller class
 *
 * @author ADRIA - LP
 */
public class FXMLNumericInfoController implements Initializable {

    private static final String DEFAULT_FILE_PATH = "src/files/Jul_20_2017_0183.NMEA";
    private static final int PRESSURE_CHARS = 6;

    @FXML
    private Text nTemp;
    @FXML
    private Text nDirection;
    @FXML
    private Text nPressure;
    @FXML
    private Text nSpeed;

    private Model model;

    /**
     * Initializes the controller class.
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
        model.barometricPressureProperty().addListener((a, b, c) -> {
            String dat = String.valueOf(c);
            String dat0 = dat + "0"; // Variable separada por funcionamiento de lambda.
            // + " " + model.getBarometricUnit()
            Platform.runLater(() -> {
                nPressure.setText((dat.length() < PRESSURE_CHARS) ? dat0 : dat);
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

    private void cargarFichero() throws FileNotFoundException {
        File file = new File(DEFAULT_FILE_PATH);
        model.addSentenceReader(file);
    }

    /*
    @FXML
    private void cargarFichero2(ActionEvent event) throws FileNotFoundException {
        FileChooser ficheroChooser = new FileChooser();
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        ficheroChooser.setInitialDirectory(new File(currentPath));
        ficheroChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("ficheros NMEA",
                                                                                 "*.NMEA"));

//        ficheroChooser.setSelectedExtensionFilter(new ExtensionFilter("ficheros NMEA", "*.NMEA"));
        ficheroChooser.setTitle("fichero datos NMEA");

        File ficheroNMEA = ficheroChooser.showOpenDialog(b.getScene().getWindow());
        if (ficheroNMEA != null) {
            // ========================================================
            // NO se comprueba que se trata de un fichero de datos NMEA
            // esto es una demos
            //ficheroLabel.setText("fichero: " + ficheroNMEA.getName());
            // ========================================================
            // se pone en marcha el proceso para recibir tramas
            model.addSentenceReader(ficheroNMEA);
        }
    }
     */
}
