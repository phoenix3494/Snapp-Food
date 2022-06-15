import controller.LoginPageController;
import controller.StartPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("\\view\\startpage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StartPageController controller = loader.getController();
        controller.initfunction(stage);

        stage.setScene(new Scene(loader.getRoot()));
        stage.setTitle("SnapFood!");
        stage.setResizable(false);
        stage.show();
    }
}