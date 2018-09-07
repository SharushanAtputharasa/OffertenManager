package ch.ksba.offertenmanager;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        
        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
//        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();

        stage.setResizable(false);
        stage.sizeToScene();
    }

    public static Stage getStage() {
        return stage;
    }

}
