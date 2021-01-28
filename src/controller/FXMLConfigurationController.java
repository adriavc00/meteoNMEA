package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import model.Model;
import util.Theme;

/**
 * FXML Controller class of the configuration frame.
 *
 * @author Adria V.
 * @author Felipe Z.
 */
public class FXMLConfigurationController implements Initializable {

    private FXMLMainController mainController;
    private Model model;

    @FXML
    private ImageView cloudIcon;
    @FXML
    private ImageView simIcon;

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
        this.model = Model.getInstance();
    }

    /**
     * Set the main controller to show other nodes and initialize the icons and the theme listener.
     *
     * @param mainController Controller to be set.
     */
    public void setMainController(FXMLMainController mainController) {
        this.mainController = mainController;
        initializeIcons(this.mainController.themeProperty().get());
        // Theme listener
        this.mainController.themeProperty().addListener((observable, oldV, newV) -> {
            initializeIcons(newV);
        });
    }

    private void initializeIcons(Theme theme) {
        switch (theme) {
            case DARK_THEME:
                cloudIcon.setImage(new Image("/resources/images/white_cloud_icon.png"));
                simIcon.setImage(new Image("/resources/images/white_file_icon.png"));
                break;
            case LIGHT_THEME:
                cloudIcon.setImage(new Image("/resources/images/black_cloud_icon.png"));
                simIcon.setImage(new Image("/resources/images/black_file_icon.png"));
                break;
            default:
                throw new AssertionError(this.mainController.themeProperty().get().name());
        }
    }

    @FXML
    private void toNotImplemented(MouseEvent event) {
        mainController.setNoImplementCenter();
    }

    @FXML
    private void toFileChooser(MouseEvent event) {
        FileChooser ficheroChooser = new FileChooser();
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        ficheroChooser.setInitialDirectory(new File(currentPath));
        ficheroChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("ficheros NMEA",
                                                                                 "*.NMEA"));
        ficheroChooser.setTitle("fichero datos NMEA");

        File ficheroNMEA = ficheroChooser.showOpenDialog(
                ((Node) event.getSource()).getScene().getWindow());
        if (ficheroNMEA != null) {
            // ========================================================
            // NO se comprueba que se trata de un fichero de datos NMEA
            // esto es una demos
            // ========================================================
            // se pone en marcha el proceso para recibir tramas
            try {
                model.addSentenceReader(ficheroNMEA);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FXMLMainController.class.getName()).log(Level.SEVERE, null, ex);
            }
            mainController.resetCharts();
            mainController.setNumericCenter();
        }
    }
}
