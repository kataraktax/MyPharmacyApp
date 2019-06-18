package sample.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;
import sample.animation.FadeInFadeOut;
import sample.database.DatabaseHandler;
import sample.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

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
    private ImageView closePanel;

    @FXML
    private ImageView updateButton;

    @FXML
    private ImageView cancelButton;

    @FXML
    private ImageView deleteUserButton;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        FadeInFadeOut fadeInFadeOut = new FadeInFadeOut();
        fadeInFadeOut.makeFadeIn(rootAnchorPane);

        DatabaseHandler databaseHandler = new DatabaseHandler();

        String loginScene = "/sample/view/login.fxml";

        User currentUser = new User();
        ResultSet userRow = databaseHandler.getUserById(LoginController.userId);

        while (userRow.next()) {
            currentUser.setFirstName(userRow.getString("firstname"));
            currentUser.setLastName(userRow.getString("lastname"));
            currentUser.setUserName(userRow.getString("username"));
            currentUser.setPassword(userRow.getString("password"));

            editFirstName.setPromptText(currentUser.getFirstName());
            editLastName.setPromptText(currentUser.getLastName());
            editUserName.setPromptText(currentUser.getUserName());
            editPassword.setPromptText("Enter new Password");
        }

        updateButton.setOnMouseClicked(event -> {
            User tempUser = new User();
            if (editFirstName.getText().equals("")) {
                tempUser.setFirstName(currentUser.getFirstName());
            } else {
                tempUser.setFirstName(editFirstName.getText().trim());
            }
            if (editLastName.getText().equals("")) {
                tempUser.setLastName(currentUser.getLastName());
            } else {
                tempUser.setLastName(editLastName.getText().trim());
            }
            if (editUserName.getText().equals("")) {
                tempUser.setUserName(currentUser.getUserName());
            } else {
                tempUser.setUserName(editUserName.getText().trim());
            }
            if (editPassword.getText().equals("")) {
                tempUser.setPassword(currentUser.getPassword());
            } else {
                tempUser.setPassword(editPassword.getText().trim());
            }

            try {
                databaseHandler.updateUser(tempUser, LoginController.userId);
                rootAnchorPane.setVisible(false);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        cancelButton.setOnMouseClicked(event -> rootAnchorPane.setVisible(false));
        closePanel.setOnMouseClicked(event -> rootAnchorPane.setVisible(false));
        deleteUserButton.setOnMouseClicked(event -> {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete User Confirmation");
                alert.setHeaderText("Please confirm do you want to continue and delete user: " + currentUser.getUserName());
                alert.initStyle(StageStyle.UTILITY);
                alert.setContentText("If you want to delete this User profile press OK in other case please press CANCEL");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    databaseHandler.deleteUser(LoginController.userId);
                    fadeInFadeOut.makeFadeOut(rootAnchorPane, loginScene);
                } else {
                    alert.close();
                }


            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

    }
}
