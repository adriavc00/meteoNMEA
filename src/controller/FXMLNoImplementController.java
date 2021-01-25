package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author lipez
 */
public class FXMLNoImplementController implements Initializable {

    private FXMLMainController mainController;

    @FXML
    private ImageView crossIcon;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setMainController(FXMLMainController mainController) {
        this.mainController = mainController;
        // Init logos
        switch (this.mainController.getTheme()) {
            case DARK_THEME:
                crossIcon.imageProperty().set(new Image("/resources/images/white_cross_icon.png"));
                break;
            case LIGHT_THEME:
                crossIcon.imageProperty().set(new Image("/resources/images/black_cross_icon.png"));
        }
    }

    @FXML
    private void backToConfiguration(ActionEvent event) {
        mainController.setConfigurationCenter();
    }

}
