package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import model.Model;

/**
 * FXML Controller class
 *
 * @author ADRIA - LP
 */
public class FXMLConfigurationController implements Initializable {

    private FXMLMainController mainController;
    private Model model;
    @FXML
    private ImageView cloudIcon;
    @FXML
    private ImageView simIcon;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.model = Model.getInstance();
    }

    /**
     * Set the main controller to show other nodes.
     *
     * @param mainController
     */
    public void setMainController(FXMLMainController mainController) {
        this.mainController = mainController;
        initializeIcons();
    }

    private void initializeIcons() {
        switch (this.mainController.getTheme()) {
            case DARK_THEME:
                cloudIcon.setImage(new Image("/resources/images/white_cloud_icon.png"));
                simIcon.setImage(new Image("/resources/images/white_file_icon.png"));
                break;
            case LIGHT_THEME:
                cloudIcon.setImage(new Image("/resources/images/black_cloud_icon.png"));
                simIcon.setImage(new Image("/resources/images/black_file_icon.png"));
        }
    }

    private void toFileChooser(ActionEvent event) {
        FileChooser ficheroChooser = new FileChooser();
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        ficheroChooser.setInitialDirectory(new File(currentPath));
        ficheroChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("ficheros NMEA",
                                                                                 "*.NMEA"));

//        ficheroChooser.setSelectedExtensionFilter(new ExtensionFilter("ficheros NMEA", "*.NMEA"));
        ficheroChooser.setTitle("fichero datos NMEA");

        File ficheroNMEA = ficheroChooser.showOpenDialog(
                ((Node) event.getSource()).getScene().getWindow());
        if (ficheroNMEA != null) {
            // ========================================================
            // NO se comprueba que se trata de un fichero de datos NMEA
            // esto es una demos
            //ficheroLabel.setText("fichero: " + ficheroNMEA.getName());
            // ========================================================
            // se pone en marcha el proceso para recibir tramas
            try {
                model.addSentenceReader(ficheroNMEA);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FXMLMainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        mainController.setNumericCenter();
    }

    private void toNotImplemented(ActionEvent event) {
        mainController.setNoImplementCenter();
    }
}
