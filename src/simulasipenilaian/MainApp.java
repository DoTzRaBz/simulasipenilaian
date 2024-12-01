package simulasipenilaian;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL fxmlUrl = getClass().getResource("/simulasipenilaian/FXMLDocument.fxml");
        if (fxmlUrl == null) {
            System.err.println("FXML file not found!");
            return;
        }

        Parent root = FXMLLoader.load(fxmlUrl);
        primaryStage.setTitle("Simulasi Penilaian F1/MotoGP");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}