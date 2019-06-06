package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;



public class Main extends Application {

    public static final Image appImage = new Image("sample/assets/icon_application.png");
    public static final String APP_NAME = "Home Pharmacy Application";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/login.fxml"));
        primaryStage.setTitle(APP_NAME);
        primaryStage.getIcons().add(appImage);
        primaryStage.setScene(new Scene(root, 1300, 900));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
