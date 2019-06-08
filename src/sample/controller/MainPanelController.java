package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import sample.animation.FadeInFadeOut;

public class MainPanelController {

    @FXML
    private AnchorPane rootAnchorPane;






    @FXML
    void initialize() {
        FadeInFadeOut fadeInFadeOut = new FadeInFadeOut();
        fadeInFadeOut.makeFadeIn(rootAnchorPane);


    }
}