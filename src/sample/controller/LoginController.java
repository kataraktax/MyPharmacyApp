package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import sample.animation.FadeInFadeOut;
import sample.database.DatabaseHandler;

public class LoginController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private JFXTextField userNameLoginText;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton createNewUserButton;

    @FXML
    private JFXPasswordField passwordLoginText;

    @FXML
    void initialize(){
        FadeInFadeOut fadeInFadeOut = new FadeInFadeOut();
        DatabaseHandler databaseHandler = new DatabaseHandler();

        String createUserScene = "/sample/view/create_user.fxml";

        fadeInFadeOut.makeFadeIn(rootAnchorPane);

        // load create user scene
        createNewUserButton.setOnAction(event -> fadeInFadeOut.makeFadeOut(rootAnchorPane, createUserScene));

    }

}

