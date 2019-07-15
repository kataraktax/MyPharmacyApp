package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import sample.animation.FadeInFadeOut;
import sample.animation.Shaker;
import sample.database.DatabaseHandler;
import sample.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    static int userId;

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
    private Label loginError;

    @FXML
    void initialize() {
        loginError.setVisible(false);
        FadeInFadeOut fadeInFadeOut = new FadeInFadeOut();
        DatabaseHandler databaseHandler = new DatabaseHandler();

        String createUserScene = "/sample/view/create_user.fxml";
        String mainPanelScene = "/sample/view/main_panel.fxml";

        fadeInFadeOut.makeFadeIn(rootAnchorPane);

        // load create user scene
        createNewUserButton.setOnAction(event -> fadeInFadeOut.makeFadeOut(rootAnchorPane, createUserScene));
        fadeInFadeOut.hoverOverButtonEffects(createNewUserButton);

        loginButton.setOnAction(event -> {

            if (userNameLoginText.getText().equals("") || passwordLoginText.getText().equals("")) {
                Shaker userNameShaker = new Shaker(userNameLoginText);
                Shaker passwordShaker = new Shaker(passwordLoginText);

                userNameShaker.shake();
                passwordShaker.shake();
                loginError.setText("Please provide User Name and Password");
                loginError.setVisible(true);
                fadeInFadeOut.hideLabel(loginError);
            } else {
                String login = userNameLoginText.getText().trim();
                String password = passwordLoginText.getText().trim();

                User tempUser = new User();
                tempUser.setUserName(login);
                tempUser.setPassword(password);

                try {
                    ResultSet userRow = databaseHandler.getUser(tempUser);
                    if (userRow.next()) {
                        userId = userRow.getInt("userid");
                        fadeInFadeOut.makeFadeOut(rootAnchorPane, mainPanelScene);
                    } else {
                        Shaker userNameShaker = new Shaker(userNameLoginText);
                        Shaker passwordShaker = new Shaker(passwordLoginText);

                        userNameShaker.shake();
                        passwordShaker.shake();
                        loginError.setText("Wrong username or password. Please try again...");
                        loginError.setVisible(true);
                        fadeInFadeOut.hideLabel(loginError);
                    }

                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
        fadeInFadeOut.hoverOverButtonEffects(loginButton);
        passwordLoginText.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER" )){
                if (userNameLoginText.getText().equals("") || passwordLoginText.getText().equals("")) {
                    Shaker userNameShaker = new Shaker(userNameLoginText);
                    Shaker passwordShaker = new Shaker(passwordLoginText);

                    userNameShaker.shake();
                    passwordShaker.shake();
                    loginError.setText("Please provide User Name and Password");
                    loginError.setVisible(true);
                    fadeInFadeOut.hideLabel(loginError);
                } else {
                    String login = userNameLoginText.getText().trim();
                    String password = passwordLoginText.getText().trim();

                    User tempUser = new User();
                    tempUser.setUserName(login);
                    tempUser.setPassword(password);

                    try {
                        ResultSet userRow = databaseHandler.getUser(tempUser);
                        if (userRow.next()) {
                            userId = userRow.getInt("userid");
                            fadeInFadeOut.makeFadeOut(rootAnchorPane, mainPanelScene);
                        } else {
                            Shaker userNameShaker = new Shaker(userNameLoginText);
                            Shaker passwordShaker = new Shaker(passwordLoginText);

                            userNameShaker.shake();
                            passwordShaker.shake();
                            loginError.setText("Wrong username or password. Please try again...");
                            loginError.setVisible(true);
                            fadeInFadeOut.hideLabel(loginError);
                        }

                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }

}

