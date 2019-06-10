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
import java.sql.SQLException;

public class CreateUserController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private JFXTextField createUserFirstName;

    @FXML
    private JFXTextField createUserLastName;

    @FXML
    private JFXTextField createUserName;

    @FXML
    private JFXPasswordField createUserPassword;

    @FXML
    private JFXButton createUserButton;

    @FXML
    private Label loginError;

    @FXML
    void initialize() {
        FadeInFadeOut fadeInFadeOut = new FadeInFadeOut();
        fadeInFadeOut.makeFadeIn(rootAnchorPane);

        loginError.setVisible(false);

        String loginScene = "/sample/view/login.fxml";

        createUserButton.setOnAction(event -> {
            if (!createUserFirstName.getText().equals("") && !createUserLastName.getText().equals("")
                    && !createUserName.getText().equals("") && !createUserPassword.getText().equals("")) {
                try {
                    createUser();
                    fadeInFadeOut.makeFadeOut(rootAnchorPane, loginScene);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                Shaker firstNameShaker = new Shaker(createUserFirstName);
                Shaker lastNameShaker = new Shaker(createUserLastName);
                Shaker userNameShaker = new Shaker(createUserName);
                Shaker passwordShaker = new Shaker(createUserPassword);

                firstNameShaker.shake();
                lastNameShaker.shake();
                userNameShaker.shake();
                passwordShaker.shake();

                loginError.setVisible(true);
                fadeInFadeOut.hideLabel(loginError);
            }
        });
    }

    private void createUser() throws SQLException, ClassNotFoundException {

        DatabaseHandler databaseHandler = new DatabaseHandler();

        String firstName = createUserFirstName.getText();
        String lastName = createUserLastName.getText();
        String userName = createUserName.getText();
        String password = createUserPassword.getText();

        User newUser = new User(firstName, lastName, userName, password);

        databaseHandler.createUser(newUser);
    }
}
