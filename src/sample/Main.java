package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/login.fxml"));
        primaryStage.setTitle("Home Pharmacy Application");
        Image image = new Image("sample/assets/icon_application.png");
        primaryStage.getIcons().add(image);
        primaryStage.setScene(new Scene(root, 1300, 900));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
