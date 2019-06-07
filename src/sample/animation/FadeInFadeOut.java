package sample.animation;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Main;

import java.io.IOException;

public class FadeInFadeOut {

    public FadeInFadeOut() {
    }

    public void makeFadeOut(Node node, String nextScene) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(node);
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(event -> {
            try {
                loadNextScene(node, nextScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fadeTransition.play();
    }

    private void loadNextScene(Node node, String resource) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource(resource));
        Scene newScene = new Scene(root);
        Stage currentStage = (Stage) node.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.setTitle(Main.APP_NAME);
        currentStage.getIcons().add(Main.appImage);

    }

    public void makeFadeIn(Node node){
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(node);
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
}
