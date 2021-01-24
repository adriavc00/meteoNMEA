package meteonmea;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Main class of the application. It contains the launcher of the main graphical container.
 *
 * @author Adrià V. C.
 * @author Felipe Z. M.
 */
public class MeteoNMEA extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLMain.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(800);
        stage.setHeight(480);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        stage.setOnHiding((event) -> {
            Platform.exit();
            System.exit(0);
        });
    }

    /**
     * Method to be runned when the application starts. It launches the app.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
