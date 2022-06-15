package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartPageController {
    Stage stage;

    public void initfunction(Stage stage) {
        this.stage = stage;
    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void login(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\loginpage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoginPageController controller = loader.getController();
        controller.initfunction(this.stage);

        this.stage.setScene(new Scene(loader.getRoot()));
    }

    @FXML
    void register(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("..\\view\\signuppage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SignUpPageController controller = loader.getController();
        controller.initfunction(this.stage);

        this.stage.setScene(new Scene(loader.getRoot()));

    }
}