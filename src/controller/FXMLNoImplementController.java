package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.Theme;

/**
 * FXML Controller class of the port connection mode, that is not implemented.
 *
 * @author Adria V.
 * @author Felipe Z.
 */
public class FXMLNoImplementController implements Initializable {

    private FXMLMainController mainController;

    @FXML
    private ImageView crossIcon;

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
                crossIcon.setImage(new Image("/resources/images/white_cross_icon.png"));
                break;
            case LIGHT_THEME:
                crossIcon.setImage(new Image("/resources/images/black_cross_icon.png"));
                break;
        }
    }

    @FXML
    private void backToConfiguration(ActionEvent event) {
        mainController.setConfigurationCenter();
    }

}
