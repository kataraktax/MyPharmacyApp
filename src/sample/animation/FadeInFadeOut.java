package sample.animation;

import com.jfoenix.controls.JFXButton;
import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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
        fadeTransition.setDuration(Duration.seconds(1.5));
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0.3);
        fadeTransition.setOnFinished(event -> {
            try {
                loadNextScene(node, nextScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fadeTransition.play();
    }
    public void quickRefresh(Node node, String scene) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(scene));
        Scene newScene = new Scene(root);
        Stage currentStage = (Stage) node.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.setTitle(Main.APP_NAME);
        currentStage.getIcons().add(Main.appImage);
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
        fadeTransition.setDuration(Duration.seconds(1.5));
        fadeTransition.setFromValue(0.3);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    public void hideLabel(Label label){
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5000), event -> label.setVisible(false)));
        timeline.play();

    }

    public void popupYPanel(AnchorPane pane, String resource, int endYValue) throws IOException {
        AnchorPane formPane = FXMLLoader.load(getClass().getResource(resource));
        pane.setVisible(true);
        formPane.translateYProperty().set(-350.00);
        pane.getChildren().setAll(formPane);

        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        KeyValue keyValue = new KeyValue(formPane.translateYProperty(), endYValue, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1.5), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public void hoverOverIconEffects(ImageView imageView){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        imageView.setOnMouseEntered(event -> imageView.setEffect(dropShadow));
        imageView.setOnMouseExited(event -> imageView.setEffect(null));
    }

    public void hoverOverButtonEffects(JFXButton button){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        button.setOnMouseEntered(event -> button.setEffect(dropShadow));
        button.setOnMouseExited(event -> button.setEffect(null));
    }
}
