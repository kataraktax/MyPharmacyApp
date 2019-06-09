package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import sample.animation.FadeInFadeOut;

public class EditProfileController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private JFXTextField editFirstName;

    @FXML
    private JFXTextField editLastName;

    @FXML
    private JFXTextField editUserName;

    @FXML
    private JFXPasswordField editPassword;

    @FXML
    private JFXButton updateUserButton;

    @FXML
    void initialize(){

        FadeInFadeOut fadeInFadeOut = new FadeInFadeOut();
        fadeInFadeOut.makeFadeIn(rootAnchorPane);



    }

}
