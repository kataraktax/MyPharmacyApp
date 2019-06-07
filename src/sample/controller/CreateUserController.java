package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.animation.FadeInFadeOut;
import sample.database.DatabaseHandler;
import sample.model.User;

import java.io.IOException;
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
    void initialize(){
        FadeInFadeOut fadeInFadeOut = new FadeInFadeOut();
        fadeInFadeOut.makeFadeIn(rootAnchorPane);

        createUserButton.setOnAction(event -> {
            try {
                createUser();

                createUserButton.getScene().getWindow().hide();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/view/login.fxml"));

                loader.load();

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } catch (SQLException | ClassNotFoundException | IOException e) {
                e.printStackTrace();
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
