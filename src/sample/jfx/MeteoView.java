package sample.jfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.Controller.MainController;
import sample.Model.InfoMeteo;
import sample.Model.SaxReader;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CountDownLatch;

/**
 * Created by axel on 11/01/2017.
 */
public class MeteoView extends Application implements Observer {


    private static final Logger LOG = LoggerFactory.getLogger(MeteoView.class);
    private static MeteoView instance = null;
    private static CountDownLatch latch;

    /**
     *  Appelle la méthode start() de JFX
     */

    public static void Launch() {
        MeteoView.launch();
    }

    public static void Launch(CountDownLatch countDownLatch) {
        latch = countDownLatch;
        MeteoView.launch();
    }


    public static MeteoView getInstance() {
        return instance;
    }

    private Stage stage = null;
    private MainController controller = null;
    private MeteoController inCtrl = null;

    /**
     *  Affiche notre view
     */

    public void display() {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                stage.show();
            }
        });
    }

    /**
     *  Ferme notre view
     */

    public void close() {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                stage.close();
            }
        });
    }


    /**
     *  Set les paramètres de notre view
     */

    @Override
    @SuppressWarnings("static-access")
    public void start(Stage primaryStage) throws Exception {
        LOG.debug("Start");

        this.instance = this;

        this.stage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample.fxml"));
        Parent root = loader.load();

        Object obj_ctrl = loader.getController();
        if (obj_ctrl instanceof MeteoController) {
            inCtrl = (MeteoController) obj_ctrl;
        }

        stage.setTitle("My Weather App");
        stage.setScene(new Scene(root, 800, 600));


        if (latch!=null)
            latch.countDown();
    }

    /**
     *  Methode de l'interface Observer, update le model
     */

    @Override
    public void update(Observable o, Object arg) {

        if (arg instanceof InfoMeteo) {
            Platform.runLater(new Runnable(){
                @Override
                public void run() {
                    inCtrl.updateTextField((InfoMeteo) arg);
                    inCtrl.setImage((InfoMeteo) arg);

                }
            });
        }
        if (arg instanceof SaxReader) {
            Platform.runLater(new Runnable(){
                @Override
                public void run() {
                    inCtrl.updateSearch((SaxReader) arg);
                }
            });
        }


    }

    /**
     *  Initialise le controller
     */

    public void initialize(MainController controller) {

        this.controller = controller;

        this.inCtrl.setController(this.controller);
    }



}
