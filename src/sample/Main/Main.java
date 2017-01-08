package sample.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.Controller.MeteoController;

import java.util.ArrayList;

public class Main extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    protected MeteoController meteoController = new MeteoController();




    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../sample.fxml"));
        primaryStage.setTitle("Weather App");
      /*  for(int i = 0; i < meteoController.getBookmarks().getItems().size() ; i++){
            LOGGER.info("value  " + i + " : " + meteoController.getBookmarksItem(i));
        }*/

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

    }


    public static void main(String[] args) throws Exception{




        launch(args);
    }
}
