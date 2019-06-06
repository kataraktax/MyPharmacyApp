package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Main;
import sample.database.DatabaseHandler;

import java.io.IOException;

public class LoginController {

    @FXML
    private JFXTextField userNameLoginText;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton createNewUserButton;

    @FXML
    private JFXPasswordField passwordLoginText;

    private DatabaseHandler databaseHandler;

    @FXML
    void initialize(){

        databaseHandler = new DatabaseHandler();

        createNewUserButton.setOnAction(event -> {

            createNewUserButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/create_user.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(Main.APP_NAME);
            stage.getIcons().add(Main.appImage);
            stage.showAndWait();

        });

    }

}

