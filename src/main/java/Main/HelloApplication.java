package Main;

import Scenas.GameScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    GameScene gameScene = new GameScene();
    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Chess");
        stage.setScene(gameScene.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}